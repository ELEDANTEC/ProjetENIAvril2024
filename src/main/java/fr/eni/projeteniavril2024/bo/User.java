package fr.eni.projeteniavril2024.bo;

import java.util.List;

public class User {
    private int userNo;
    private String pseudo;
    private String lastName;
    private String firstName;
    private String email;
    private String telephoneNumber;
    private String road;
    private String city;
    private int postcode;
    private String password;
    private int credit;
    private boolean admin;
    private List<ItemSold> itemsSold;
    private List<ItemSold> itemsPurchased;
    private List<Bid> bidsPlaced;

    public User() {
    }

    public User(int userNo, String pseudo, String lastName, String firstName, String email, String telephoneNumber, String road, String city, int postcode, String password, int credit, boolean admin) {
        this.userNo = userNo;
        this.pseudo = pseudo;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.telephoneNumber = telephoneNumber;
        this.road = road;
        this.city = city;
        this.postcode = postcode;
        this.password = password;
        this.credit = credit;
        this.admin = admin;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<ItemSold> getItemsSold() {
        return itemsSold;
    }

    public void setItemsSold(List<ItemSold> itemsSold) {
        this.itemsSold = itemsSold;
    }

    public void addItemSold(ItemSold itemSold) {
        this.itemsSold.add(itemSold);
    }

    public void removeItemSold(ItemSold itemSold) {
        this.itemsSold.remove(itemSold);
    }

    public List<ItemSold> getItemsPurchased() {
        return itemsPurchased;
    }

    public void setItemsPurchased(List<ItemSold> itemsPurchased) {
        this.itemsPurchased = itemsPurchased;
    }

    public void addItemPurchased(ItemSold itemPurchased) {
        this.itemsPurchased.add(itemPurchased);
    }

    public void removeItemPurchased(ItemSold itemPurchased) {
        this.itemsPurchased.remove(itemPurchased);
    }

    public List<Bid> getBidsPlaced() {
        return bidsPlaced;
    }

    public void setBidsPlaced(List<Bid> bidsPlaced) {
        this.bidsPlaced = bidsPlaced;
    }

    public void addBidPlaced(Bid bidPlaced) {
        this.bidsPlaced.add(bidPlaced);
    }

    public void removeBidPlaced(Bid bidPlaced) {
        this.bidsPlaced.remove(bidPlaced);
    }
}
