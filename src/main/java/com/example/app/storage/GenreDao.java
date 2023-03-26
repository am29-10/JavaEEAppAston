package com.example.app.storage;

import com.example.app.model.Genre;

import java.util.List;

public interface GenreDao {
    Genre create(Genre genre);

    List<Genre> readAll();

    Genre update(int id, Genre genre);

    Genre getGenreById(int id);
}
