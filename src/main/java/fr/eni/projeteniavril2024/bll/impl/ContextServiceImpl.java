package fr.eni.projeteniavril2024.bll.impl;

import fr.eni.projeteniavril2024.bll.ContextService;
import fr.eni.projeteniavril2024.bo.User;
import fr.eni.projeteniavril2024.dal.UserDAO;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class ContextServiceImpl implements ContextService {
    private final UserDAO userDAO;

    public ContextServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User load(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public List<User> loadAll() {
        return userDAO.findAll();
    }
}
