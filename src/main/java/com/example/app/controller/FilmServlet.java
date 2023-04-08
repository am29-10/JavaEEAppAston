package com.example.app.controller;

import com.example.app.model.Film;
import com.example.app.model.Mpa;
import com.example.app.service.FilmService;
import com.example.app.service.MpaService;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/film/add", "/film/update", "/film/get-all", "/film/get"})
public class  FilmServlet extends HttpServlet {
    private FilmService filmService;
    private MpaService mpaService;

    public FilmServlet(FilmService filmService, MpaService mpaService) {
        this.filmService = filmService;
        this.mpaService = mpaService;
    }

    @Override
    public void init() {
        filmService = new FilmService();
        mpaService = new MpaService();
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
            List<Film> filmList = filmService.readAll();
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
            Mpa mpa = mpaService.getMpaById(mpaId);
            Film newFilm = new Film(name, description, duration, mpa);
            filmService.create(newFilm);
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
            Mpa mpa = mpaService.getMpaById(mpaId);
            Film updatedFilm = new Film(id, name, description, duration, mpa);
            filmService.update(id, updatedFilm);
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
            Film film = filmService.getFilmById(id);
            PrintWriter pw = response.getWriter();
            pw.println(film.getName());
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
