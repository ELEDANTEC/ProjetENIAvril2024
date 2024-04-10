package fr.eni.projeteniavril2024.dal;

import fr.eni.projeteniavril2024.bo.SoldItem;
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
        int expectedSoldItemsCount = 1;
        String expectedSoldItemName = "PC Gamer pour travailler";
        LocalDate expectedSoldItemStartAuctionDate = LocalDate.of(2024, 4, 9);
        int expectedSoldItemInitialPrice = 185;
        int expectedUserId = 1;
        int expectedCategoryId = 1;

        List<SoldItem> soldItems = soldItemDAO.findAll();

        assertNotNull(soldItems);
        assertEquals(expectedSoldItemsCount, soldItems.size());
        assertEquals(expectedSoldItemName, soldItems.get(0).getItemName());
        assertEquals(expectedSoldItemStartAuctionDate, soldItems.get(0).getStartAuctionDate());
        assertEquals(expectedSoldItemInitialPrice, soldItems.get(0).getInitialPrice());
        assertEquals(expectedUserId, soldItems.get(0).getUser().getUserId());
        assertEquals(expectedCategoryId, soldItems.get(0).getCategory().getCategoryId());
    }

    @Test
    void test02_soldItem_findById() {
        int soldItemId = 1;
        String expectedSoldItemName = "PC Gamer pour travailler";
        LocalDate expectedSoldItemStartAuctionDate = LocalDate.of(2024, 4, 9);
        int expectedSoldItemInitialPrice = 185;
        int expectedUserId = 1;
        int expectedCategoryId = 1;

        SoldItem soldItem = soldItemDAO.findById(soldItemId);

        assertNotNull(soldItem);
        assertEquals(expectedSoldItemName, soldItem.getItemName());
        assertEquals(expectedSoldItemStartAuctionDate, soldItem.getStartAuctionDate());
        assertEquals(expectedSoldItemInitialPrice, soldItem.getInitialPrice());
        assertEquals(expectedUserId, soldItem.getUser().getUserId());
        assertEquals(expectedCategoryId, soldItem.getCategory().getCategoryId());
    }
}
