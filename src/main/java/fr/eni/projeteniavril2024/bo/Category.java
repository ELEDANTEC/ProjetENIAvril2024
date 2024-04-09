package fr.eni.projeteniavril2024.bo;

/**
 * Business model for a category
 * @version 1.0
 * @author yjeauneau
 */
public class Category {
    private int categoryNo;
    private String wording;

    public Category() {
    }

    public Category(String wording, int categoryNo) {
        this.wording = wording;
        this.categoryNo = categoryNo;
    }

    public int getCategoryNo() {
        return categoryNo;
    }

    public void setCategoryNo(int categoryNo) {
        this.categoryNo = categoryNo;
    }

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }
}
