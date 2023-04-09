package com.example.app.storage.impl;

import com.example.app.db.Connector;
import com.example.app.model.MotionPictureAssociation;
import com.example.app.storage.MpaDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MpaDaoImplTest {
    MotionPictureAssociation mpa;
    MpaDao mpaDao;

    @BeforeEach
    void beforeEach() {
        mpa = new MotionPictureAssociation("X", "На сеанс не допускаются лица, не достигшие 17-летнего возраста");
        mpaDao = new MpaDaoImpl("h2");

        Connector connector = new Connector("h2");
        connector.setJDBC_URL("jdbc:h2:./db/films;INIT=runscript from 'src/main/resources/schema.sql'");
        connector.getConnection();
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