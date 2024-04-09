package fr.eni.projeteniavril2024.dal;

import fr.eni.projeteniavril2024.bo.Category;

import java.util.List;

public interface CategoryDAO {
    List<Category> findAll();
    Category findById(int id);
}
