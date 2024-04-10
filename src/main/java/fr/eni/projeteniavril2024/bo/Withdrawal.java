package fr.eni.projeteniavril2024.bo;

/**
 * Business model for a withdrawal
 * @version 1.0
 * @author yjeauneau
 */
public class Withdrawal {
    private int itemId;
    private String street;
    private String city;
    private String postalCode;

    public Withdrawal() {
    }

    public Withdrawal(
            int itemId,
            String street,
            String city,
            String postalCode
    ) {
        this.itemId = itemId;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
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
}
