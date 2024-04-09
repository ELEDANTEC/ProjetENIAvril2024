package fr.eni.tp.movies.dal.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCUserDAO implements UserDAO {

    private Connection connection;

    public JDBCUserDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addUser(User user) {
        // Implémentation pour ajouter un utilisateur à la base de données
    }

    @Override
    public User getUserById(int userId) {
        // Implémentation pour récupérer un utilisateur par son identifiant
    }

    @Override
    public List<User> getAllUsers() {
        // Implémentation pour récupérer tous les utilisateurs de la base de données
    }

    @Override
    public void updateUser(User user) {
        // Implémentation pour mettre à jour les informations d'un utilisateur dans la base de données
    }

    @Override
    public void deleteUser(int userId) {
        // Implémentation pour supprimer un utilisateur de la base de données
    }
}