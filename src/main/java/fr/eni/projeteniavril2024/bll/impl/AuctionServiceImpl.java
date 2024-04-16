package fr.eni.projeteniavril2024.bll.impl;

import fr.eni.projeteniavril2024.bll.AuctionService;
import fr.eni.projeteniavril2024.bo.Category;
import fr.eni.projeteniavril2024.bo.SoldItem;
import fr.eni.projeteniavril2024.bo.User;
import fr.eni.projeteniavril2024.dal.BidDAO;
import fr.eni.projeteniavril2024.dal.CategoryDAO;
import fr.eni.projeteniavril2024.dal.SoldItemDAO;
import fr.eni.projeteniavril2024.dal.UserDAO;
import fr.eni.projeteniavril2024.exception.BusinessException;
import fr.eni.projeteniavril2024.exception.BusinessCode;
import org.springframework.cglib.core.Local;
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

    AuctionServiceImpl(
            SoldItemDAO soldItemDAO,
            CategoryDAO categoryDAO,
            BidDAO bidDAO,
            UserDAO userDAO
    ) {
        this.soldItemDAO = soldItemDAO;
        this.categoryDAO = categoryDAO;
        this.bidDAO = bidDAO;
        this.userDAO = userDAO;
    }

    @Override
    public List<SoldItem> getAuctions() {
        List<SoldItem> auctions = soldItemDAO.findAll();
        if (!auctions.isEmpty()) {
            auctions.forEach(auction -> {
                auction.setBids(bidDAO.findAll(auction.getItemId()));
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
            if (LocalDate.now().isAfter(soldItem.getEndAuctionDate())) {
                soldItem.setSaleStatus("Enchères terminées");
            } else if (LocalDate.now().isBefore(soldItem.getStartAuctionDate())) {
                soldItem.setSaleStatus("En cours");
            } else {
                soldItem.setSaleStatus("Créée");
            }
        }
        return soldItem;
    }

    @Override
    public void createAuction(SoldItem auction) {
        BusinessException businessException = new BusinessException();
        boolean isValid = true;
        isValid &= isAuctionValid(auction, businessException);
        if (isValid) {
            isValid &= isAuctionNameValid(auction.getItemName(), businessException);
            isValid &= isAuctionDescriptionValid(auction.getDescription(), businessException);
            isValid &= isAuctionDateValid(auction.getStartAuctionDate(), auction.getEndAuctionDate(), businessException);
            isValid &= isAuctionSellerValid(auction.getSeller(), businessException);
            isValid &= isAuctionCategoryValid(auction.getCategory(), businessException);
        }
        if (isValid) {
            auction.setItemId(soldItemDAO.create(auction));
        } else {
            businessException.add(BusinessCode.BLL_AUCTION_CREATE_ERROR);
            throw businessException;
        }
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

//    VALIDATION AUCTION

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

    public boolean isAuctionDateValid(
            LocalDate startAuctionDate,
            LocalDate endAuctionDate,
            BusinessException businessException
    ) {
        if (startAuctionDate == null) {
            businessException.add(BusinessCode.VALIDATION_AUCTION_DATE);
            return false;
        }
        if (LocalDate.now().isAfter(startAuctionDate)) {
            businessException.add(BusinessCode.VALIDATION_AUCTION_DATE_START);
            return false;
        }
        if (LocalDate.now().isAfter(endAuctionDate) || startAuctionDate.isAfter(endAuctionDate)) {
            businessException.add(BusinessCode.VALIDATION_AUCTION_DATE_END);
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
}
