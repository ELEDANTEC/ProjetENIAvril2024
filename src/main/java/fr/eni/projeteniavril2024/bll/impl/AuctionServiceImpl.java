package fr.eni.projeteniavril2024.bll.impl;

import fr.eni.projeteniavril2024.bll.AuctionService;
import fr.eni.projeteniavril2024.bo.*;
import fr.eni.projeteniavril2024.dal.*;
import fr.eni.projeteniavril2024.exception.BusinessCode;
import fr.eni.projeteniavril2024.exception.BusinessException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Primary
public class AuctionServiceImpl implements AuctionService {
    private final SoldItemDAO soldItemDAO;
    private final CategoryDAO categoryDAO;
    private final BidDAO bidDAO;
    private final UserDAO userDAO;
    private final WithdrawalDAO withdrawalDAO;

    AuctionServiceImpl(
            SoldItemDAO soldItemDAO,
            CategoryDAO categoryDAO,
            BidDAO bidDAO,
            UserDAO userDAO,
            WithdrawalDAO withdrawalDAO
    ) {
        this.soldItemDAO = soldItemDAO;
        this.categoryDAO = categoryDAO;
        this.bidDAO = bidDAO;
        this.userDAO = userDAO;
        this.withdrawalDAO = withdrawalDAO;
    }

    @Override
    public List<SoldItem> getAuctions() {
        List<SoldItem> auctions = soldItemDAO.findAll();
        if (!auctions.isEmpty()) {
            auctions.forEach(auction -> {
                List<Bid> bids = bidDAO.findAll(auction.getItemId());
                auction.setBids(bids);
                if (LocalDate.now().isAfter(auction.getEndAuctionDate())) {
                    auction.setSaleStatus("Enchères terminées");
                } else if (LocalDate.now().isAfter(auction.getStartAuctionDate())) {
                    auction.setSaleStatus("En cours");
                } else {
                    auction.setSaleStatus("Créée");
                }
            });
        }
        return auctions;
    }

    @Override
    public SoldItem getAuctionById(int id) {
        SoldItem soldItem = soldItemDAO.findById(id);
        if (soldItem != null) {
            soldItem.setBids(bidDAO.findAll(soldItem.getItemId()));
            if (LocalDate.now().isBefore(soldItem.getStartAuctionDate())) {
                soldItem.setSaleStatus("Créée");
            } else if (LocalDate.now().isBefore(soldItem.getEndAuctionDate())) {
                soldItem.setSaleStatus("En cours");
            } else {
                soldItem.setSaleStatus("Enchères terminées");
            }
        }
        return soldItem;
    }

    @Override
    public List<Category> getCategories() {
        return categoryDAO.findAll();
    }

    @Override
    public Category getCategoryById(int id) {
        Category category = null;
        if (id > 0) {
            category = categoryDAO.findById(id);
        }
        return category;
    }

    @Override
    public void createAuction(SoldItem auction) {
        BusinessException businessException = new BusinessException();
        boolean isValid = true;
        isValid &= isAuctionValid(auction, businessException);
        if (isValid) {
            isValid &= isAuctionNameValid(auction.getItemName(), businessException);
            isValid &= isAuctionDescriptionValid(auction.getDescription(), businessException);
            isValid &= isAuctionDateStartValid(auction.getStartAuctionDate(), businessException);
            isValid &= isAuctionDateEndValid(auction.getStartAuctionDate(), auction.getEndAuctionDate(), businessException);
            isValid &= isAuctionSellerValid(auction.getSeller(), businessException);
            isValid &= isAuctionCategoryValid(auction.getCategory(), businessException);
            isValid &= isAuctionWithdrawalValid(auction.getWithdrawal(), businessException);
            isValid &= isAuctionWithdrawalStreetValid(auction.getWithdrawal().getStreet(), businessException);
            isValid &= isAuctionWithdrawalPostalCodeValid(auction.getWithdrawal().getPostalCode(), businessException);
            isValid &= isAuctionWithdrawalCityValid(auction.getWithdrawal().getCity(), businessException);
        }
        if (isValid) {
            auction.setItemId(soldItemDAO.create(auction));
            auction.getWithdrawal().setItemId(auction.getItemId());
            withdrawalDAO.create(auction.getWithdrawal());
        } else {
            businessException.add(BusinessCode.BLL_AUCTION_CREATE_ERROR);
            throw businessException;
        }
    }

    @Override
    public void createBid(Bid bid) {
        BusinessException businessException = new BusinessException();
        boolean isValid = true;
        isValid &= isBidValid(bid, businessException);
        if (isValid) {
            isValid &= isBidAmountValid(bid, businessException);
            isValid &= isCreditSufficientValid(bid, businessException);
        }
        if (isValid) {
            User buyer = bid.getBuyer();
            if (!bid.getAuction().getBids().isEmpty()) {
                User lastBuyer = bid.getAuction().getBids().getLast().getBuyer();
                int lastBidAmount = bid.getAuction().getBids().getLast().getBidAmount();
                lastBuyer.setCredit(lastBuyer.getCredit() + lastBidAmount);
                userDAO.updateCredit(lastBuyer.getUserId(), lastBuyer.getCredit());
                if (lastBuyer.getUserId() == buyer.getUserId()) {
                    buyer = lastBuyer;
                }
                boolean existingBid = false;
                for (Bid el : bid.getAuction().getBids()) {
                    if (el.getBuyer().getUserId() == buyer.getUserId()) {
                        existingBid = true;
                        break;
                    }
                }
                if (existingBid) {
                    bidDAO.delete(buyer.getUserId(), bid.getAuction().getItemId());
                }
            }
            buyer.setCredit(buyer.getCredit() - bid.getBidAmount());
            userDAO.updateCredit(buyer.getUserId(), buyer.getCredit());
            bidDAO.create(bid);
        } else {
            businessException.add(BusinessCode.BLL_BID_CREATE_ERROR);
            throw businessException;
        }
    }

    @Override
    public void updateAuction(SoldItem auction) {
        soldItemDAO.update(auction);
        withdrawalDAO.update(auction.getWithdrawal());
    }

    @Override
    public void deleteAuction(int itemId) {
        SoldItem auction = soldItemDAO.findById(itemId);
        if (auction.getBids() != null && !auction.getBids().isEmpty()) {
            for (Bid bid : auction.getBids()) {
                bidDAO.delete(bid.getBuyer().getUserId(), bid.getAuction().getItemId());
            }
        }
        if (auction.getWithdrawal() != null) {
            withdrawalDAO.delete(itemId);
        }
        soldItemDAO.delete(itemId);
    }

    //    AUCTION VALIDATION

    public boolean isAuctionValid(
            SoldItem auction,
            BusinessException businessException
    ) {
        if (auction == null) {
            businessException.add(BusinessCode.VALIDATION_AUCTION_NULL);
            return false;
        }
        if (soldItemDAO.uniqueSoldItem(auction) > 0) {
            businessException.add(BusinessCode.VALIDATION_AUCTION_UNIQUE);
            return false;
        }
        return true;
    }

    public boolean isAuctionNameValid(
            String auctionName,
            BusinessException businessException
    ) {
        if (auctionName == null || auctionName.isBlank()) {
            businessException.add(BusinessCode.VALIDATION_AUCTION_NAME_BLANK);
            return false;
        }
        if (auctionName.length() > 30) {
            businessException.add(BusinessCode.VALIDATION_AUCTION_NAME_LENGTH);
            return false;
        }
        return true;
    }

    public boolean isAuctionDescriptionValid(
            String auctionDescription,
            BusinessException businessException
    ) {
        if (auctionDescription == null || auctionDescription.isBlank()) {
            businessException.add(BusinessCode.VALIDATION_AUCTION_DESCRIPTION_BLANK);
            return false;
        }
        if (auctionDescription.length() > 300) {
            businessException.add(BusinessCode.VALIDATION_AUCTION_DESCRIPTION_LENGTH);
            return false;
        }
        return true;
    }

    public boolean isAuctionDateStartValid(
            LocalDate startAuctionDate,
            BusinessException businessException
    ) {
        if (startAuctionDate == null) {
            businessException.add(BusinessCode.VALIDATION_AUCTION_DATE_START_NULL);
            return false;
        }
        if (LocalDate.now().isAfter(startAuctionDate)) {
            businessException.add(BusinessCode.VALIDATION_AUCTION_DATE_START_WRONG);
            return false;
        }
        return true;
    }

    public boolean isAuctionDateEndValid(
            LocalDate startAuctionDate,
            LocalDate endAuctionDate,
            BusinessException businessException
    ) {
        if (endAuctionDate == null) {
            businessException.add(BusinessCode.VALIDATION_AUCTION_DATE_END_NULL);
            return false;
        }
        if (startAuctionDate.isAfter(endAuctionDate)) {
            businessException.add(BusinessCode.VALIDATION_AUCTION_DATE_END_WRONG);
            return false;
        }
        return true;
    }

    public boolean isAuctionSellerValid(
            User auctionSeller,
            BusinessException businessException
    ) {
        if (auctionSeller == null) {
            businessException.add(BusinessCode.VALIDATION_AUCTION_SELLER_NULL);
            return false;
        }
        if (userDAO.existingUser(auctionSeller.getUserId()) < 1) {
            businessException.add(BusinessCode.VALIDATION_AUCTION_SELLER_ID_UNKNOWN);
            return false;
        }
        return true;
    }

    public boolean isAuctionCategoryValid(
            Category auctionCategory,
            BusinessException businessException
    ) {
        if (auctionCategory == null) {
            businessException.add(BusinessCode.VALIDATION_AUCTION_CATEGORY_NULL);
            return false;
        }
        if (categoryDAO.existingCategory(auctionCategory.getCategoryId()) < 1) {
            businessException.add(BusinessCode.VALIDATION_AUCTION_CATEGORY_ID_UNKNOWN);
            return false;
        }
        return true;
    }

    public boolean isAuctionWithdrawalValid(
            Withdrawal auctionWithdrawal,
            BusinessException businessException
    ) {
        if (auctionWithdrawal == null) {
            businessException.add(BusinessCode.VALIDATION_AUCTION_WITHDRAWAL_NULL);
            return false;
        }
        return true;
    }

    public boolean isAuctionWithdrawalStreetValid(
            String auctionWithdrawalStreet,
            BusinessException businessException
    ) {
        if (auctionWithdrawalStreet == null) {
            businessException.add(BusinessCode.VALIDATION_AUCTION_WITHDRAWAL_STREET_BLANK);
            return false;
        }
        if (auctionWithdrawalStreet.length() > 30) {
            businessException.add(BusinessCode.VALIDATION_AUCTION_WITHDRAWAL_STREET_LENGTH);
            return false;
        }
        return true;
    }

    public boolean isAuctionWithdrawalPostalCodeValid(
            String auctionWithdrawalPostalCode,
            BusinessException businessException
    ) {
        if (auctionWithdrawalPostalCode == null) {
            businessException.add(BusinessCode.VALIDATION_AUCTION_WITHDRAWAL_POSTAL_CODE_BLANK);
            return false;
        }
        if (auctionWithdrawalPostalCode.length() > 5) {
            businessException.add(BusinessCode.VALIDATION_AUCTION_WITHDRAWAL_POSTAL_CODE_LENGTH);
            return false;
        }
        return true;
    }

    public boolean isAuctionWithdrawalCityValid(
            String auctionWithdrawalCity,
            BusinessException businessException
    ) {
        if (auctionWithdrawalCity == null) {
            businessException.add(BusinessCode.VALIDATION_AUCTION_WITHDRAWAL_CITY_BLANK);
            return false;
        }
        if (auctionWithdrawalCity.length() > 30) {
            businessException.add(BusinessCode.VALIDATION_AUCTION_WITHDRAWAL_CITY_LENGTH);
            return false;
        }
        return true;
    }

//    BID VALIDATION

    public boolean isBidValid(
            Bid bid,
            BusinessException businessException
    ) {
        if (bid == null) {
            businessException.add(BusinessCode.VALIDATION_BID_NULL);
            return false;
        }
        return true;
    }

    public boolean isBidAmountValid(
            Bid bid,
            BusinessException businessException
    ) {
        if (!bid.getAuction().getBids().isEmpty()) {
            if (bid.getBidAmount() <= bid.getAuction().getBids().getLast().getBidAmount()) {
                businessException.add(BusinessCode.VALIDATION_BID_AMOUNT_SMALLER);
                return false;
            }
        } else {
            if (bid.getBidAmount() <= bid.getAuction().getInitialPrice()) {
                businessException.add(BusinessCode.VALIDATION_BID_AMOUNT_SMALLER);
                return false;
            }
        }
        return true;
    }

    public boolean isCreditSufficientValid(
            Bid bid,
            BusinessException businessException
    ) {
        if (bid.getBuyer().getCredit() < bid.getBidAmount()) {
            businessException.add(BusinessCode.VALIDATION_BID_CREDIT_INSUFFICIENT);
            return false;
        }
        return true;
    }


}
