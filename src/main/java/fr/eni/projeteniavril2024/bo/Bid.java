package fr.eni.projeteniavril2024.bo;

import java.time.LocalDateTime;

/**
 * Business model for a bid
 * @version 1.0
 * @author yjeauneau
 */
public class Bid {
    private int userId;
    private int itemId;
    private LocalDateTime bidDate;
    private int bidAmount;

    public Bid() {
    }

    public Bid(
            int userId,
            int itemId,
            LocalDateTime bidDate,
            int bidAmount
    ) {
        this.userId = userId;
        this.itemId = itemId;
        this.bidDate = bidDate;
        this.bidAmount = bidAmount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
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
}
