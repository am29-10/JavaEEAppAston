package com.example.app.storage.impl;

import com.example.app.db.Connector;
import com.example.app.model.Genre;
import com.example.app.storage.GenreDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenreDaoImplTest {

    Genre genre;
    GenreDao genreDao;

    @BeforeEach
    void beforeEach() {
        genre = new Genre("Художественный");
        genreDao = new GenreDaoImpl("h2");

        Connector connector = new Connector("h2");
        connector.setJDBC_URL("jdbc:h2:./db/films;INIT=runscript from 'src/main/resources/schema.sql'");
        connector.getConnection();
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