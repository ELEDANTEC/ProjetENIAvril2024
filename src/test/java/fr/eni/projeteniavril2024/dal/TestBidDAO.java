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
        int expectedBidCount = 2;
        int expectedFirstBidUserId = 1;
        int expectedFirstBidBidAmount = 200;
        LocalDateTime expectedFirstBidBidDate = LocalDateTime.of(2024, 4, 8, 0, 0);

        List<Bid> bids = bidDAO.findAll(1);

        assertNotNull(bids);
        assertEquals(expectedBidCount, bids.size());
        assertEquals(expectedFirstBidUserId, bids.get(0).getUserId());
        assertEquals(expectedFirstBidBidAmount, bids.get(0).getBidAmount());
        assertEquals(expectedFirstBidBidDate, bids.get(0).getBidDate());
    }
}
