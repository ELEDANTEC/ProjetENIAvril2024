package fr.eni.projeteniavril2024.dal;

import fr.eni.projeteniavril2024.bo.Withdrawal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TestWithdrawalDAO {
    @Autowired
    private WithdrawalDAO withdrawalDAO;

    @Test
    void test01_withdrawal_findByItemId() {
        int itemId = 1;
        String expectedWithdrawalStreet = "rue du test";
        String expectedWithdrawalPostalCode = "29000";
        String expectedWithdrawalCity = "quimper";

        Withdrawal withdrawal = withdrawalDAO.findByItemId(itemId);

        assertNotNull(withdrawal);
        assertEquals(expectedWithdrawalStreet, withdrawal.getStreet());
        assertEquals(expectedWithdrawalPostalCode, withdrawal.getPostalCode());
        assertEquals(expectedWithdrawalCity, withdrawal.getCity());
    }
}
