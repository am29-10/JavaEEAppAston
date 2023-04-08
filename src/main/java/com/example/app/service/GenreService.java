package com.example.app.service;

import com.example.app.model.Genre;
import com.example.app.storage.GenreDao;
import com.example.app.storage.impl.GenreDaoImpl;

import java.util.List;

public class GenreService {
    GenreDao genreDao;

    public GenreService() {
        genreDao = new GenreDaoImpl();
    }

    public Genre create(Genre genre) {
        return genreDao.create(genre);
    }

    public List<Genre> readAll() {
        return genreDao.readAll();
    }

    public Genre update(int id, Genre genre) {
        return genreDao.update(id, genre);
    }

    public Genre getGenreById(int id) {
        return genreDao.getGenreById(id);
    }


}
