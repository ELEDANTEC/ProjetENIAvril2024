package fr.eni.projeteniavril2024.bll;

import fr.eni.projeteniavril2024.bo.User;

import java.util.List;

public interface ContextService {
    User load(String email);
    List<User> loadAll();
}
