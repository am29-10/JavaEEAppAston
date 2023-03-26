package com.example.app.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilmTest {

    Film film;

    Mpa mpa;

    @BeforeEach
    void beforeEach() {
        mpa = new Mpa(1, "G", "У фильма нет возрастных ограничений");
        film = new Film(1,"Гарри Потер", "История мальчика, который выжил и его друзей", 3,
                mpa);
    }

    @Test
    void getId() {
        assertEquals(1, film.getId());
    }

    @Test
    void setId() {
        film.setId(2);
        assertEquals(2, film.getId());
    }

    @Test
    void getName() {
        assertEquals("Гарри Потер", film.getName());
    }

    @Test
    void setName() {
        film.setName("Гарри Потер и тайная комната");
        assertEquals("Гарри Потер и тайная комната", film.getName());
    }

    @Test
    void getDescription() {
        assertEquals("История мальчика, который выжил и его друзей", film.getDescription());
    }

    @Test
    void getDuration() {
        assertEquals(3, film.getDuration());
    }

    @Test
    void getMpa() {
        assertEquals(mpa, film.getMpa());
    }

    @Test
    void setMpa() {
        Mpa newMpa = new Mpa(2, "PG", "Детям рекомендуется смотреть фильм с родителями");
        film.setMpa(newMpa);
        assertEquals(newMpa, film.getMpa());
    }

    @Test
    void getGenres() {
        assertNull(film.getGenres());
    }

    @Test
    void setGenres() {
        film.setGenres(List.of(new Genre("Фантастика")));
        assertNotNull(film.getGenres());
    }
}