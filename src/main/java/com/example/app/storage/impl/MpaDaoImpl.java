package com.example.app.storage.impl;

import com.example.app.db.Connector;
import com.example.app.model.MotionPictureAssociation;
import com.example.app.storage.MpaDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MpaDaoImpl implements MpaDao {
    Connector connector;
    public MpaDaoImpl() {
        connector = new Connector();
    }

    public MpaDaoImpl(String nameDB) {
        connector = new Connector(nameDB);
    }

    @Override
    public MotionPictureAssociation create(MotionPictureAssociation mpa) {
        try (Connection connection = connector.getConnection();
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
    public List<MotionPictureAssociation> readAll() {
        List<MotionPictureAssociation> mpaList = new ArrayList<>();
        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM MPA")) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                mpaList.add(new MotionPictureAssociation(id, name, description));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return mpaList;
    }

    @Override
    public MotionPictureAssociation update(int id, MotionPictureAssociation mpa) {
        try (Connection connection = connector.getConnection();
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
    public MotionPictureAssociation getMpaById(int id) {
        MotionPictureAssociation mpa = null;
        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM MPA WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                mpa = new MotionPictureAssociation(id, name, description);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return mpa;
    }

}
