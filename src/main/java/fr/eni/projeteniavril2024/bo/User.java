package fr.eni.projeteniavril2024.bo;

import org.springframework.core.style.ToStringCreator;

import java.util.List;

/**
 * Business model for a user
 * @version 1.0
 * @author yjeauneau
 */
public class User {
    private int userId;
    private String username;
    private String lastName;
    private String firstName;
    private String email;
    private String phone;
    private String street;
    private String city;
    private String postalCode;
    private String password;
    //confirmer le mot de passe, attribut non hashé, non lié en bdd
    private String plainPassword;
    private int credit;
    private boolean administrator;
    
    private List<SoldItem> itemsSold;
    private List<SoldItem> itemsPurchased;
    private List<Bid> bidsPlaced;

    public User() {
    }

    public User(
            int userId,
            String username,
            String lastName,
            String firstName,
            String email,
            boolean administrator
    ) {
        this.userId = userId;
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.administrator = administrator;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

    public List<SoldItem> getItemsSold() {
        return itemsSold;
    }

    public void setItemsSold(List<SoldItem> itemsSold) {
        this.itemsSold = itemsSold;
    }

    public void addItemSold(SoldItem soldItem) {
        this.itemsSold.add(soldItem);
    }

    public void removeItemSold(SoldItem soldItem) {
        this.itemsSold.remove(soldItem);
    }

    public List<SoldItem> getItemsPurchased() {
        return itemsPurchased;
    }

    public void setItemsPurchased(List<SoldItem> itemsPurchased) {
        this.itemsPurchased = itemsPurchased;
    }

    public void addItemPurchased(SoldItem itemPurchased) {
        this.itemsPurchased.add(itemPurchased);
    }

    public void removeItemPurchased(SoldItem itemPurchased) {
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

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", password='" + password + '\'' +
                ", credit=" + credit +
                ", administrator=" + administrator +
                ", itemsSold=" + itemsSold +
                ", itemsPurchased=" + itemsPurchased +
                ", bidsPlaced=" + bidsPlaced +
                '}';
    }
}
