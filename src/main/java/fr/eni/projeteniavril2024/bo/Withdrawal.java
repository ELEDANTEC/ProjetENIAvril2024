package fr.eni.projeteniavril2024.bo;

public class Withdrawal {
    private String road;
    private String city;
    private int postcode;

    public Withdrawal() {
    }

    public Withdrawal(String road, String city, int postcode) {
        this.road = road;
        this.city = city;
        this.postcode = postcode;
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
}
