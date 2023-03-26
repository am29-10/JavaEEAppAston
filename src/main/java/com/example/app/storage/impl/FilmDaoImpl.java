package com.example.app.storage.impl;

import com.example.app.model.Film;
import com.example.app.model.Mpa;
import com.example.app.storage.FilmDao;
import com.example.app.storage.MpaDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmDaoImpl implements FilmDao {
    private String JDBC_URL;
    private String USERNAME;
    private String PASSWORD;
    private String CLASS_NAME;
    private MpaDao mpaDao;

    public FilmDaoImpl(String nameDB) {
        if (nameDB.equals("postgres")) {
            JDBC_URL = "jdbc:postgresql://localhost:5432/filmsDB";
            USERNAME = "admin";
            PASSWORD = "admin";
            CLASS_NAME = "org.postgresql.Driver";
            mpaDao = new MpaDaoImpl("postgres");
        } else if (nameDB.equals("h2")) {
            JDBC_URL = "jdbc:h2:./db/films;DB_CLOSE_DELAY=-1";
            USERNAME = "sa";
            PASSWORD = "password";
            CLASS_NAME = "org.h2.Driver";
            mpaDao = new MpaDaoImpl("h2");
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
    public Film create(Film film) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO FILMS (name, description, " +
                     "duration, mpa_id) VALUES (?, ?, ?, ?)")) {
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setInt(3, film.getDuration());
            ps.setInt(4, film.getMpa().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return film;
    }

    @Override
    public List<Film> readAll() {
        List<Film> filmList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT id, name, description, " +
                     "duration, mpa_id FROM FILMS ")) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int duration = rs.getInt("duration");
                int mpaId = rs.getInt("mpa_id");
                Mpa mpa = mpaDao.getMpaById(mpaId);
                filmList.add(new Film(id, name, description, duration, mpa));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return filmList;
    }

    @Override
    public Film update(int id, Film film) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE FILMS SET name=?, description=?, " +
                     "duration=?, mpa_id=? WHERE id=?")) {
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setInt(3, film.getDuration());
            ps.setInt(4, film.getMpa().getId());
            ps.setInt(5, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return film;
    }

    @Override
    public Film getFilmById(int id) {
        Film film = null;
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * " +
                     "FROM FILMS " +
                     "JOIN MPA ON FILMS.MPA_ID = MPA.ID " +
                     "WHERE FILMS.ID = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                int duration = rs.getInt("duration");
                int mpaId = rs.getInt("mpa_id");
                Mpa mpa = mpaDao.getMpaById(mpaId);
                film = new Film(id, name, description, duration, mpa);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return film;
    }
}
