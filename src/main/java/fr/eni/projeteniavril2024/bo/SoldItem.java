package fr.eni.projeteniavril2024.bo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Business model for a sold item
 * @version 1.0
 * @author yjeauneau
 */
public class SoldItem {
    private int itemId;
    private String itemName;
    private String description;
    private LocalDate startAuctionDate;
    private LocalDate endAuctionDate;
    private int initialPrice;
    private int salePrice;
    private String saleStatus;

    private User seller;
    private Category category;
    private Withdrawal withdrawal;
    private List<Bid> bids;

    public SoldItem() {
    }

    public SoldItem(
            int itemId,
            String itemName,
            String description,
            LocalDate startAuctionDate,
            LocalDate endAuctionDate,
            int initialPrice,
            String saleStatus
    ) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.description = description;
        this.startAuctionDate = startAuctionDate;
        this.endAuctionDate = endAuctionDate;
        this.initialPrice = initialPrice;
        this.saleStatus = saleStatus;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartAuctionDate() {
        return startAuctionDate;
    }

    public void setStartAuctionDate(LocalDate startAuctionDate) {
        this.startAuctionDate = startAuctionDate;
    }

    public LocalDate getEndAuctionDate() {
        return endAuctionDate;
    }

    public void setEndAuctionDate(LocalDate endAuctionDate) {
        this.endAuctionDate = endAuctionDate;
    }

    public int getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(int initialPrice) {
        this.initialPrice = initialPrice;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Withdrawal getWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(Withdrawal withdrawal) {
        this.withdrawal = withdrawal;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public void addBid(Bid bid) {
        if (this.bids == null) {
            this.bids = new ArrayList<>();
        }
        this.bids.add(bid);
    }

    public void removeBid(Bid bid) {
        this.bids.remove(bid);
    }
}
