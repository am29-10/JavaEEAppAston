package com.example.app.controller;

import com.example.app.model.Film;
import com.example.app.model.Mpa;
import com.example.app.storage.FilmDao;
import com.example.app.storage.MpaDao;
import com.example.app.storage.impl.FilmDaoImpl;
import com.example.app.storage.impl.MpaDaoImpl;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/film/add", "/film/update", "/film/get-all", "/film/get"})
public class  FilmServlet extends HttpServlet {
    private FilmDao filmDao;
    private MpaDao mpaDao;
    List<Film> filmList = new ArrayList<>();

    public FilmServlet() {
        this(new FilmDaoImpl("postgres"),
                new MpaDaoImpl("postgres"));
    }

    public FilmServlet(FilmDao filmDao, MpaDao mpaDao) {
        this.filmDao = filmDao;
        this.mpaDao = mpaDao;
    }

    @Override
    public void init() {
        filmDao = new FilmDaoImpl("postgres");
        mpaDao = new MpaDaoImpl("postgres");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getServletPath();

        switch (action) {
            case "/film/add":
                addFilm(request, response);
                break;
            case "/film/update":
                updateFilm(request, response);
                break;
            case "/film/get":
                getFilm(request, response);
                break;
            default:
                getAllFilm(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    private void getAllFilm(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            filmList = filmDao.readAll();
            request.setAttribute("filmList", filmList);
            PrintWriter pw = response.getWriter();
            for (Film film : filmList) {
                pw.println(film.getName());
            }
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addFilm(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            int duration = Integer.parseInt(request.getParameter("duration"));
            int mpaId = Integer.parseInt(request.getParameter("mpa_id"));
            Mpa mpa = mpaDao.getMpaById(mpaId);
            Film newFilm = new Film(name, description, duration, mpa);
            filmDao.create(newFilm);
            pw.println("Фильм с названием " + newFilm.getName() + " добавлен в БД");
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateFilm(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            int duration = Integer.parseInt(request.getParameter("duration"));
            int mpaId = Integer.parseInt(request.getParameter("mpa_id"));
            Mpa mpa = mpaDao.getMpaById(mpaId);
            Film updatedFilm = new Film(id, name, description, duration, mpa);
            filmDao.update(id, updatedFilm);
            pw.println("Фильм для id = " + id + " обновлен");
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getFilm(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            int id = Integer.parseInt(request.getParameter("id"));
            Film film = filmDao.getFilmById(id);
            PrintWriter pw = response.getWriter();
            pw.println(film.getName());
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
