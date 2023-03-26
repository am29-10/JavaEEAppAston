package com.example.app.storage.impl;

import com.example.app.model.Mpa;
import com.example.app.storage.MpaDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MpaDaoImpl implements MpaDao {

    private String JDBC_URL;
    private String USERNAME;
    private String PASSWORD;
    private String CLASS_NAME;

    public MpaDaoImpl(String nameDB) {
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
    public Mpa create(Mpa mpa) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO MPA (name, description) " +
                     "VALUES (?, ?)")) {
            ps.setString(1, mpa.getName());
            ps.setString(2, mpa.getDescription());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return mpa;
    }

    @Override
    public List<Mpa> readAll() {
        List<Mpa> mpaList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM MPA")) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                mpaList.add(new Mpa(id, name, description));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return mpaList;
    }

    @Override
    public Mpa update(int id, Mpa mpa) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE MPA SET name=?, description=? WHERE id=?")) {
            ps.setString(1, mpa.getName());
            ps.setString(2, mpa.getDescription());
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return mpa;
    }

    @Override
    public Mpa getMpaById(int id) {
        Mpa mpa = null;
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM MPA WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                mpa = new Mpa(id, name, description);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return mpa;
    }

}
