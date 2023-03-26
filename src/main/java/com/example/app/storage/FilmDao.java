package com.example.app.storage;

import com.example.app.model.Film;

import java.util.List;

public interface FilmDao {
    Film create(Film film);

    List<Film> readAll();

    Film update(int id, Film film);

    Film getFilmById(int id);
}
