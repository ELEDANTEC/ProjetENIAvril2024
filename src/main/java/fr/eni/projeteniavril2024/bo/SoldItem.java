package fr.eni.projeteniavril2024.bo;

/**
 * Business model for a sold item
 * @version 1.0
 * @author yjeauneau
 */
public class SoldItem {
    private int itemId;
    private String itemName;
    private String description;
    private String startAuctionDate;
    private String endAuctionDate;
    private int initialPrice;
    private int salePrice;
    private String saleStatus;

    private Category category;
    private Withdrawal withdrawal;

    public SoldItem() {
    }

    public SoldItem(
            int itemId,
            String itemName,
            String description,
            String startAuctionDate,
            String endAuctionDate,
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

    public String getStartAuctionDate() {
        return startAuctionDate;
    }

    public void setStartAuctionDate(String startAuctionDate) {
        this.startAuctionDate = startAuctionDate;
    }

    public String getEndAuctionDate() {
        return endAuctionDate;
    }

    public void setEndAuctionDate(String endAuctionDate) {
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
}
