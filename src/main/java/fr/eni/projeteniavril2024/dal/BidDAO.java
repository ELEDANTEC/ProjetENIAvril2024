package fr.eni.projeteniavril2024.dal;

import fr.eni.projeteniavril2024.bo.Bid;

import java.util.List;

public interface BidDAO {
    List<Bid> findAll(int itemId);
    void create(Bid bid);
}
