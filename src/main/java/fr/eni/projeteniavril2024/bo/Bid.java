package fr.eni.projeteniavril2024.bo;

/**
 * Business model for a bid
 * @version 1.0
 * @author yjeauneau
 */
public class Bid {
    private String bidDate;
    private int bidPrice;
    private ItemSold itemSold;

    public Bid() {
    }

    public Bid(String bidDate, int bidPrice) {
        this.bidDate = bidDate;
        this.bidPrice = bidPrice;
    }

    public String getBidDate() {
        return bidDate;
    }

    public int getBidPrice() {
        return bidPrice;
    }

    public void setBidDate(String bidDate) {
        this.bidDate = bidDate;
    }

    public void setBidPrice(int bidPrice) {
        this.bidPrice = bidPrice;
    }

    public ItemSold getItemSold() {
        return itemSold;
    }

    public void setItemSold(ItemSold itemSold) {
        this.itemSold = itemSold;
    }
}
