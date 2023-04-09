package com.example.app.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MpaTest {
    MotionPictureAssociation mpa;

    @BeforeEach
    void beforeEach() {
        mpa = new MotionPictureAssociation(1, "X", "На сеанс не допускаются лица, не достигшие 17-летнего возраста");
    }

    @Test
    void getId() {
        assertEquals(1, mpa.getId());
    }

    @Test
    void setId() {
        mpa.setId(2);
        assertEquals(2, mpa.getId());
    }

    @Test
    void getName() {
        assertEquals("X", mpa.getName());
    }

    @Test
    void setName() {
        mpa.setName("XL");
        assertEquals("XL", mpa.getName());
    }

    @Test
    void getDescription() {
        assertEquals("На сеанс не допускаются лица, не достигшие 17-летнего возраста", mpa.getDescription());
    }
}