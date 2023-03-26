package com.example.app.storage.impl;

import com.example.app.model.Genre;
import com.example.app.storage.GenreDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreDaoImpl implements GenreDao {

    private String JDBC_URL;
    private String USERNAME;
    private String PASSWORD;
    private String CLASS_NAME;

    public GenreDaoImpl(String nameDB) {
        if (nameDB.equals("postgres")) {
            JDBC_URL = "jdbc:postgresql://localhost:5432/filmsDB";
            USERNAME = "admin";
            PASSWORD = "admin";
            CLASS_NAME = "org.postgresql.Driver";
        } else if (nameDB.equals("h2")) {
            JDBC_URL = "jdbc:h2:./db/films;DB_CLOSE_DELAY=-1";
            USERNAME = "sa";
            PASSWORD = "password";
            CLASS_NAME = "org.h2.Driver";
        }
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(CLASS_NAME);
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    @Override
    public Genre create(Genre genre) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO genres (name) VALUES (?)")) {
            ps.setString(1, genre.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return genre;
    }

    @Override
    public List<Genre> readAll() {
        List<Genre> genreList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM GENRES")) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                genreList.add(new Genre(id, name));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return genreList;
    }

    @Override
    public Genre update(int id, Genre genre) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE GENRES SET name=? WHERE id=?")) {
            ps.setString(1, genre.getName());
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return genre;
    }

    @Override
    public Genre getGenreById(int id) {
        Genre genre = null;
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM GENRES WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                genre = new Genre(id, name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return genre;
    }
}
