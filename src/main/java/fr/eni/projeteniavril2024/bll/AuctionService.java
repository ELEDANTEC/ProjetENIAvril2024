package fr.eni.projeteniavril2024.bll;

import fr.eni.projeteniavril2024.bo.Category;
import fr.eni.projeteniavril2024.bo.SoldItem;

import java.util.List;

public interface AuctionService {
    List<SoldItem> getAuctions();
    SoldItem getAuctionById(int id);
    void createAuction(SoldItem auction);
    List<Category> getCategories();
    Category getCategoryById(int id);
}
