package fr.eni.projeteniavril2024.bo;

/**
 * Business model for a category
 * @version 1.0
 * @author yjeauneau
 */
public class Category {
    private int categoryId;
    private String label;

    public Category() {
    }

    public Category(
            int categoryId,
            String label
    ) {
        this.categoryId = categoryId;
        this.label = label;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
