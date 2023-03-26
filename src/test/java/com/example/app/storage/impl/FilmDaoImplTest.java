package com.example.app.storage.impl;

import com.example.app.model.Film;
import com.example.app.storage.FilmDao;
import com.example.app.storage.MpaDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class FilmDaoImplTest {

    Film film;
    FilmDao filmDao;

    @BeforeEach
    void beforeEach() {
        MpaDao mpaDao = new MpaDaoImpl("h2");
        film = new Film("Гарри Потер", "История мальчика, который выжил и его друзей", 3,
                mpaDao.getMpaById(1));
        filmDao = new FilmDaoImpl("h2");
        filmDao.create(film);

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
        assertEquals("Гарри Потер", filmDao.create(film).getName());
        assertEquals(1, filmDao.readAll().size());
    }

    @Test
    void readAll() {
        filmDao.create(film);
        assertEquals(1, filmDao.readAll().size());
    }

    @Test
    void update() {
        filmDao.create(film);
        film.setName("Гарри Потер и тайная комната");
        assertEquals("Гарри Потер и тайная комната", filmDao.update(1, film).getName());
        assertEquals("Гарри Потер и тайная комната", filmDao.getFilmById(1).getName());
    }

    @Test
    void getFilmById() {
        filmDao.create(film);
        assertEquals("Гарри Потер", filmDao.getFilmById(1).getName());
    }
}