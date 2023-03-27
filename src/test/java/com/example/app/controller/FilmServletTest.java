package com.example.app.controller;

import com.example.app.model.Film;
import com.example.app.model.Mpa;
import com.example.app.storage.FilmDao;
import com.example.app.storage.MpaDao;
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

class FilmServletTest {
    private FilmDao filmDao;
    private MpaDao mpaDao;
    private FilmServlet filmServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter writer;

    @BeforeEach
    public void beforeEach() throws IOException {
        filmDao = mock(FilmDao.class);
        mpaDao = mock(MpaDao.class);
        filmServlet = new FilmServlet(filmDao, mpaDao);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        writer = new StringWriter();

        when(response.getWriter()).thenReturn(new PrintWriter(writer));
    }

    @Test
    void getAllFilm() {
        List<Film> filmList = new ArrayList<>();

        when(mpaDao.getMpaById(1)).thenReturn(new Mpa("G", "У фильма нет возрастных ограничений"));
        filmList.add(new Film("Гарри Потер", "История мальчика, который выжил и его друзей", 3,
                mpaDao.getMpaById(1)));

        when(filmDao.readAll()).thenReturn(filmList);

        when(request.getServletPath()).thenReturn("/film/get-all");

        filmServlet.doGet(request, response);
        System.out.println(filmDao.readAll());

        verify(request, times(1)).getServletPath();
    }

    @Test
    void addFilm() {
        when(request.getServletPath()).thenReturn("/film/add");

        when(request.getParameter("name")).thenReturn("Гарри Потер");
        when(request.getParameter("description")).thenReturn("История мальчика, который выжил и его друзей");
        when(request.getParameter("duration")).thenReturn("3");
        when(request.getParameter("mpa_id")).thenReturn("1");

        filmServlet.doGet(request, response);

        Assertions.assertEquals("Фильм с названием Гарри Потер добавлен в БД\r\n", writer.toString());
    }

    @Test
    void updateFilm() {
        when(request.getServletPath()).thenReturn("/film/update");

        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("Гарри Потер");
        when(request.getParameter("description")).thenReturn("История мальчика, который выжил и его друзей");
        when(request.getParameter("duration")).thenReturn("3");
        when(request.getParameter("mpa_id")).thenReturn("1");

        filmServlet.doGet(request, response);

        Assertions.assertEquals("Фильм для id = 1 обновлен\r\n", writer.toString());
    }

    @Test
    void getFilm() {
        when(mpaDao.getMpaById(1)).thenReturn(new Mpa(1,"G", "У фильма нет возрастных ограничений"));

        when(filmDao.getFilmById(1)).thenReturn(new Film(1,"Гарри Потер", "История мальчика, который " +
                "выжил и его друзей", 3, new Mpa(1,"G", "У фильма нет возрастных ограничений")));

        when(request.getServletPath()).thenReturn("/film/get");

        when(request.getParameter("id")).thenReturn("1");

        filmServlet.doGet(request, response);

        verify(request, times(1)).getServletPath();
    }
}