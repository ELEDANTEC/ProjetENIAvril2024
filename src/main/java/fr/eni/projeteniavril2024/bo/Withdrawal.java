package fr.eni.projeteniavril2024.bo;

/**
 * Business model for a withdrawal
 * @version 1.0
 * @author yjeauneau
 */
public class Withdrawal {
    private String street;
    private String city;
    private int postalCode;

    public Withdrawal() {
    }

    public Withdrawal(
            String street,
            String city,
            int postalCode
    ) {
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
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

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }
}
