package com.example.app.storage.impl;

import com.example.app.db.Connector;
import com.example.app.model.Genre;
import com.example.app.storage.GenreDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreDaoImpl implements GenreDao {

    Connector connector;

    public GenreDaoImpl() {
        connector = new Connector();
    }

    public GenreDaoImpl(String nameDB) {
        connector = new Connector(nameDB);
    }

    @Override
    public Genre create(Genre genre) {
        try (Connection connection = connector.getConnection();
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
        try (Connection connection = connector.getConnection();
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
        try (Connection connection = connector.getConnection();
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
        try (Connection connection = connector.getConnection();
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
