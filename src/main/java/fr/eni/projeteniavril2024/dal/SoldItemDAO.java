package fr.eni.projeteniavril2024.dal;

import fr.eni.projeteniavril2024.bo.SoldItem;

import java.util.List;

public interface SoldItemDAO {
    List<SoldItem> findAll();
    SoldItem findById(int id);
    int create(SoldItem soldItem);
    void update(SoldItem soldItem);
    void delete(int itemId);
    int uniqueSoldItem(SoldItem soldItem);
}
