package com.example.app.storage.impl;

import com.example.app.model.Genre;
import com.example.app.storage.GenreDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class GenreDaoImplTest {

    Genre genre;
    GenreDao genreDao;

    @BeforeEach
    void beforeEach() {
        genre = new Genre("Художественный");
        genreDao = new GenreDaoImpl("h2");

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
        assertEquals("Художественный", genreDao.create(genre).getName());
    }

    @Test
    void readAll() {
        assertEquals(6, genreDao.readAll().size());
    }

    @Test
    void update() {
        assertEquals("Художественный", genreDao.update(1, genre).getName());
        assertEquals("Художественный", genreDao.getGenreById(1).getName());
    }

    @Test
    void getGenreById() {
        assertEquals("Комедия", genreDao.getGenreById(1).getName());
    }
}