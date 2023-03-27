package com.example.app.controller;

import com.example.app.model.Genre;
import com.example.app.storage.GenreDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class GenreServletTest {
    private GenreDao genreDao;
    private GenreServlet genreServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter writer;

    @BeforeEach
    public void beforeEach() throws IOException {
        genreDao = mock(GenreDao.class);
        genreServlet = new GenreServlet(genreDao);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        writer = new StringWriter();

        when(response.getWriter()).thenReturn(new PrintWriter(writer));
    }

    @Test
    void getAllGenre() {
        List<Genre> genreList = new ArrayList<>();
        genreList.add(new Genre("Комедия"));
        genreList.add(new Genre("Драма"));
        genreList.add(new Genre( "Мультфильм"));
        genreList.add(new Genre("Триллер"));
        genreList.add(new Genre( "Документальный"));
        genreList.add(new Genre( "Боевик-1"));

        when(genreDao.readAll()).thenReturn(genreList);

        when(request.getServletPath()).thenReturn("/genre/get-all");

        genreServlet.doGet(request, response);

        verify(request, times(1)).getServletPath();

    }

    @Test
    void addGenre() {
        when(request.getServletPath()).thenReturn("/genre/add");

        when(request.getParameter("name")).thenReturn("Художественный");

        genreServlet.doGet(request, response);

        Assertions.assertEquals("Жанр с названием Художественный добавлен в БД\r\n", writer.toString());
    }

    @Test
    void updateGenre() {
        when(request.getServletPath()).thenReturn("/genre/update");

        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("Художественный");

        genreServlet.doGet(request, response);

        Assertions.assertEquals("Жанр для id = 1 обновлен\r\n", writer.toString());
    }

    @Test
    void getGenre() {
        when(genreDao.getGenreById(1)).thenReturn(new Genre(1,"Фантастика"));

        when(request.getServletPath()).thenReturn("/genre/get");

        when(request.getParameter("id")).thenReturn("1");

        genreServlet.doGet(request, response);

        verify(request, times(1)).getServletPath();
    }
}