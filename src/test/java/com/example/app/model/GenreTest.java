package com.example.app.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenreTest {

    Genre genre;

    @BeforeEach
    void beforeEach() {
        genre = new Genre(1, "Фантастика");
    }

    @Test
    void getId() {
        assertEquals(1, genre.getId());
    }

    @Test
    void setId() {
        genre.setId(2);
        assertEquals(2, genre.getId());
    }

    @Test
    void getName() {
        assertEquals("Фантастика", genre.getName());
    }

    @Test
    void setName() {
        genre.setName("Ужас");
        assertEquals("Ужас", genre.getName());
    }
}