package fr.eni.projeteniavril2024.dal;

import fr.eni.projeteniavril2024.bo.Category;
import fr.eni.projeteniavril2024.bo.SoldItem;
import fr.eni.projeteniavril2024.bo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TestSoldItemDAO {
    @Autowired
    private SoldItemDAO soldItemDAO;

    @Test
    void test01_soldItem_findAll() {
        int expectedSoldItemsCount = 8;
        String expectedFirstSoldItemName = "PC Gamer pour travailler";
        LocalDate expectedFirstSoldItemStartAuctionDate = LocalDate.of(2024, 4, 9);
        int expectedFirstSoldItemInitialPrice = 185;
        int expectedFirstSoldItemUserId = 3;
        int expectedFirstSoldItemCategoryId = 1;

        List<SoldItem> soldItems = soldItemDAO.findAll();

        assertNotNull(soldItems);
        assertEquals(expectedSoldItemsCount, soldItems.size());
        assertEquals(expectedFirstSoldItemName, soldItems.get(0).getItemName());
        assertEquals(expectedFirstSoldItemStartAuctionDate, soldItems.get(0).getStartAuctionDate());
        assertEquals(expectedFirstSoldItemInitialPrice, soldItems.get(0).getInitialPrice());
        assertEquals(expectedFirstSoldItemUserId, soldItems.get(0).getSeller().getUserId());
        assertEquals(expectedFirstSoldItemCategoryId, soldItems.get(0).getCategory().getCategoryId());
    }

    @Test
    void test02_soldItem_findById() {
        int soldItemId = 1;
        String expectedSoldItemName = "PC Gamer pour travailler";
        LocalDate expectedSoldItemStartAuctionDate = LocalDate.of(2024, 4, 9);
        int expectedSoldItemInitialPrice = 185;
        int expectedUserId = 3;
        int expectedCategoryId = 1;

        SoldItem soldItem = soldItemDAO.findById(soldItemId);

        assertNotNull(soldItem);
        assertEquals(expectedSoldItemName, soldItem.getItemName());
        assertEquals(expectedSoldItemStartAuctionDate, soldItem.getStartAuctionDate());
        assertEquals(expectedSoldItemInitialPrice, soldItem.getInitialPrice());
        assertEquals(expectedUserId, soldItem.getSeller().getUserId());
        assertEquals(expectedCategoryId, soldItem.getCategory().getCategoryId());
    }

    @Test
    void test03_soldItem_uniqueSoldItem() {
        SoldItem soldItem = new SoldItem();
        soldItem.setItemName("PC Gamer pour travailler");
        soldItem.setDescription("PC de Gamer idéale pour le télé-travail, mais aussi pour jouer.");
        Category category = new Category();
        category.setCategoryId(1);
        soldItem.setCategory(category);
        User seller = new User();
        seller.setUserId(3);
        soldItem.setSeller(seller);
        int expectedCount = 1;

        int count = soldItemDAO.uniqueSoldItem(soldItem);

        assertEquals(expectedCount, count);
    }
}
