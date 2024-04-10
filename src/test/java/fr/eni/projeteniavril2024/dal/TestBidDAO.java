package fr.eni.projeteniavril2024.dal;

import fr.eni.projeteniavril2024.bo.Bid;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TestBidDAO {
    @Autowired
    private BidDAO bidDAO;

    @Test
    void test01_bid_findAll() {
        int expectedBidCount = 1;
        int expectedUserId = 1;
        int expectedBidAmount = 150;
        LocalDateTime expectedBidDate = LocalDateTime.of(2024, 4, 10, 0, 0);

        List<Bid> bids = bidDAO.findAll(1);

        assertNotNull(bids);
        assertEquals(expectedBidCount, bids.size());
        assertEquals(expectedUserId, bids.get(0).getUserId());
        assertEquals(expectedBidAmount, bids.get(0).getBidAmount());
        assertEquals(expectedBidDate, bids.get(0).getBidDate());
    }
}
