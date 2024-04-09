package fr.eni.projeteniavril2024.bo;

public class ItemSold {
    private int itemNo;
    private String itemName;
    private String description;
    private String auctionStartDate;
    private String auctionEndDate;
    private int priceSetting;
    private int sellingPrice;
    private String sellsStatus;
    private Category category;
    private Withdrawal withdrawal;

    public ItemSold() {
    }

    public ItemSold(int itemNo, String itemName, String description, String auctionStartDate, String auctionEndDate, int priceSetting, int sellingPrice, String sellsStatus) {
        this.itemNo = itemNo;
        this.itemName = itemName;
        this.description = description;
        this.auctionStartDate = auctionStartDate;
        this.auctionEndDate = auctionEndDate;
        this.priceSetting = priceSetting;
        this.sellingPrice = sellingPrice;
        this.sellsStatus = sellsStatus;
    }

    public int getItemNo() {
        return itemNo;
    }

    public void setItemNo(int itemNo) {
        this.itemNo = itemNo;
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

    public String getAuctionStartDate() {
        return auctionStartDate;
    }

    public void setAuctionStartDate(String auctionStartDate) {
        this.auctionStartDate = auctionStartDate;
    }

    public String getAuctionEndDate() {
        return auctionEndDate;
    }

    public void setAuctionEndDate(String auctionEndDate) {
        this.auctionEndDate = auctionEndDate;
    }

    public int getPriceSetting() {
        return priceSetting;
    }

    public void setPriceSetting(int priceSetting) {
        this.priceSetting = priceSetting;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(int sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getSellsStatus() {
        return sellsStatus;
    }

    public void setSellsStatus(String sellsStatus) {
        this.sellsStatus = sellsStatus;
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
