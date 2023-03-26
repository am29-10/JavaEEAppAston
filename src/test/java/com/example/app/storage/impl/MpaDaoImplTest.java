package com.example.app.storage.impl;

import com.example.app.model.Mpa;
import com.example.app.storage.MpaDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MpaDaoImplTest {
    Mpa mpa;
    MpaDao mpaDao;

    @BeforeEach
    void beforeEach() {
        mpa = new Mpa("X", "На сеанс не допускаются лица, не достигшие 17-летнего возраста");
        mpaDao = new MpaDaoImpl("h2");

        String JDBC_URL = "jdbc:h2:./db/films;INIT=runscript from 'src/main/resources/schema.sql'";
        String USERNAME = "sa";
        String PASSWORD = "password";
        String CLASS_NAME = "org.h2.Driver";
        try {
            Connection connection = null;
            Class.forName(CLASS_NAME);
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void create() {
        assertEquals("X", mpaDao.create(mpa).getName());
    }

    @Test
    void readAll() {
        assertEquals(5, mpaDao.readAll().size());
    }

    @Test
    void update() {
        assertEquals("X", mpaDao.update(1, mpa).getName());
        assertEquals("X", mpaDao.getMpaById(1).getName());
    }

    @Test
    void getMpaById() {
        assertEquals("G", mpaDao.getMpaById(1).getName());
    }
}