package fr.eni.projeteniavril2024.dal;

import fr.eni.projeteniavril2024.bo.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TestCategoryDAO {
    @Autowired
    private CategoryDAO categoryDAO;

    @Test
    void test01_category_findAll() {
        int expectedCategoriesCount = 4;
        String expectedFirstCategoryLabel = "Informatique";
        String expectedLastCategoryLabel = "Sport&Loisirs";

        List<Category> categories = categoryDAO.findAll();

        assertNotNull(categories);
        assertEquals(expectedCategoriesCount, categories.size());
        assertEquals(expectedFirstCategoryLabel, categories.get(0).getLabel());
        assertEquals(expectedLastCategoryLabel, categories.get(categories.size() - 1).getLabel());
    }

    @Test
    void test02_category_findById() {
        int categoryId = 1;
        String expectedCategoryLabel = "Informatique";

        Category category = categoryDAO.findById(categoryId);

        assertNotNull(category);
        assertEquals(expectedCategoryLabel, category.getLabel());
    }

    @Test
    void test03_category_existingCategory() {
        int categoryId = 1;
        int expectedCount = 1;

        int count = categoryDAO.existingCategory(categoryId);

        assertEquals(expectedCount, count);
    }
}
