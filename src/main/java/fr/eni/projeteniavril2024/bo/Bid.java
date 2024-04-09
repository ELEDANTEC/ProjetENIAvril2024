package fr.eni.projeteniavril2024.bo;

/**
 * Business model for a bid
 * @version 1.0
 * @author yjeauneau
 */
public class Bid {
    private String bidDate;
    private int bidAmount;
    
    private SoldItem item;

    public Bid() {
    }

    public Bid(
            String bidDate,
            int bidAmount
    ) {
        this.bidDate = bidDate;
        this.bidAmount = bidAmount;
    }

    public String getBidDate() {
        return bidDate;
    }

    public int getBidAmount() {
        return bidAmount;
    }

    public void setBidDate(String bidDate) {
        this.bidDate = bidDate;
    }

    public void setBidAmount(int bidAmount) {
        this.bidAmount = bidAmount;
    }

    public SoldItem getItem() {
        return item;
    }

    public void setItem(SoldItem item) {
        this.item = item;
    }
}
