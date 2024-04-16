package fr.eni.projeteniavril2024.bll.impl;

import fr.eni.projeteniavril2024.bll.AuctionService;
import fr.eni.projeteniavril2024.bo.Category;
import fr.eni.projeteniavril2024.bo.SoldItem;
import fr.eni.projeteniavril2024.dal.BidDAO;
import fr.eni.projeteniavril2024.dal.CategoryDAO;
import fr.eni.projeteniavril2024.dal.SoldItemDAO;
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

    AuctionServiceImpl(
            SoldItemDAO soldItemDAO,
            CategoryDAO categoryDAO,
            BidDAO bidDAO
    ) {
        this.soldItemDAO = soldItemDAO;
        this.categoryDAO = categoryDAO;
        this.bidDAO = bidDAO;
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
//        BusinessException businessException = new BusinessException();
//        boolean isValid = true;
//        isValid &= isAuctionValid(auction, businessException);
//        if (isValid) {
//            isValid &= isAuctionValid(auction.getItemName(), businessException);
//            isValid &= isAuctionValid(auction.getDescription(), businessException);
//            isValid &= isAuctionValid(auction.getStartAuctionDate(), businessException);
//            isValid &= isAuctionValid(auction.getEndAuctionDate(), businessException);
//            isValid &= isAuctionValid(auction.getInitialPrice(), businessException);
//        }
//        if (isValid) {
//            auction.setItemId(soldItemDAO.create(auction));
//        } else {
//            businessException.add(BusinessCode.BLL_MOVIE_CREATE_ERROR);
//            throw businessException;
//        }
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
}
