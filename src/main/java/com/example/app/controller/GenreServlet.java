package com.example.app.controller;

import com.example.app.model.Genre;
import com.example.app.storage.GenreDao;
import com.example.app.storage.impl.GenreDaoImpl;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/genre/add", "/genre/update", "/genre/get-all", "/genre/get"})
public class GenreServlet extends HttpServlet {

    private GenreDao genreDao = new GenreDaoImpl("postgres");;
    List<Genre> genreList = new ArrayList<>();

    @Override
    public void init() {
        genreDao = new GenreDaoImpl("postgres");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getServletPath();

        switch (action) {
            case "/genre/add":
                addGenre(request, response);
                break;
            case "/genre/update":
                updateGenre(request, response);
                break;
            case "/genre/get":
                getGenre(request, response);
                break;
            default:
                getAllGenre(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    private void getAllGenre(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            genreList = genreDao.readAll();
            PrintWriter pw = response.getWriter();
            for (Genre genre : genreList) {
                pw.println(genre.getName());
            }
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addGenre(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            String name = request.getParameter("name");
            Genre newGenre = new Genre(name);
            genreDao.create(newGenre);
            pw.println("Жанр с названием " + newGenre.getName() + " добавлен в БД");
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateGenre(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            Genre updatedGenre = new Genre(id, name);
            genreDao.update(id, updatedGenre);
            pw.println("Жанр для id = " + id + " обновлен");
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getGenre(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            int id = Integer.parseInt(request.getParameter("id"));
            Genre genre = genreDao.getGenreById(id);
            PrintWriter pw = response.getWriter();
            pw.println(genre.getName());
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
