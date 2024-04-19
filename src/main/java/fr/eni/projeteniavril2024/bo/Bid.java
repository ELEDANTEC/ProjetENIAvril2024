package fr.eni.projeteniavril2024.bo;

import java.time.LocalDateTime;

/**
 * Business model for a bid
 * @version 1.0
 * @author yjeauneau
 */
public class Bid {
    private LocalDateTime bidDate;
    private int bidAmount;

    private User buyer;
    private SoldItem auction;

    public Bid() {
    }

    public Bid(
            LocalDateTime bidDate,
            int bidAmount
    ) {
        this.bidDate = bidDate;
        this.bidAmount = bidAmount;
    }

    public LocalDateTime getBidDate() {
        return bidDate;
    }

    public void setBidDate(LocalDateTime bidDate) {
        this.bidDate = bidDate;
    }

    public int getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(int bidAmount) {
        this.bidAmount = bidAmount;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public SoldItem getAuction() {
        return auction;
    }

    public void setAuction(SoldItem auction) {
        this.auction = auction;
    }
}
