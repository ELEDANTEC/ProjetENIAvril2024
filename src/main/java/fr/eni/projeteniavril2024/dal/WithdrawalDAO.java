package fr.eni.projeteniavril2024.dal;

import fr.eni.projeteniavril2024.bo.Withdrawal;

public interface WithdrawalDAO {
    Withdrawal findByItemId(int itemId);
    void create(Withdrawal withdrawal);
    void update(Withdrawal withdrawal);
    void delete(int itemId);
}
