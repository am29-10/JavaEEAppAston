package com.example.app.service;

import com.example.app.model.Film;
import com.example.app.storage.FilmDao;
import com.example.app.storage.impl.FilmDaoImpl;

import java.util.List;

public class FilmService {
    FilmDao filmDao;

    public FilmService() {
        filmDao = new FilmDaoImpl();
    }

    public Film create(Film film) {
        return filmDao.create(film);
    }

    public List<Film> readAll() {
        return filmDao.readAll();
    }

    public Film update(int id, Film film) {
        return filmDao.update(id, film);
    }

    public Film getFilmById(int id) {
        return filmDao.getFilmById(id);
    }
}
