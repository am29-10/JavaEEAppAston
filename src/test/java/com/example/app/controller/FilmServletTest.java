package com.example.app.controller;

import com.example.app.model.Film;
import com.example.app.model.MotionPictureAssociation;
import com.example.app.service.FilmService;
import com.example.app.service.MpaService;
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
    private FilmService filmService;
    private MpaService mpaService;
    private FilmServlet filmServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter writer;

    @BeforeEach
    public void beforeEach() throws IOException {
        filmService = mock(FilmService.class);
        mpaService = mock(MpaService.class);
        filmServlet = new FilmServlet(filmService, mpaService);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        writer = new StringWriter();

        when(response.getWriter()).thenReturn(new PrintWriter(writer));
    }

    @Test
    void getAllFilm() {
        List<Film> filmList = new ArrayList<>();

        when(mpaService.getMpaById(1)).thenReturn(new MotionPictureAssociation("G", "У фильма нет возрастных ограничений"));
        filmList.add(new Film("Гарри Потер", "История мальчика, который выжил и его друзей", 3,
                mpaService.getMpaById(1)));

        when(filmService.readAll()).thenReturn(filmList);

        when(request.getServletPath()).thenReturn("/film/get-all");

        filmServlet.doGet(request, response);
        System.out.println(filmService.readAll());

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
        when(mpaService.getMpaById(1)).thenReturn(new MotionPictureAssociation(1,"G", "У фильма нет возрастных ограничений"));

        when(filmService.getFilmById(1)).thenReturn(new Film(1,"Гарри Потер", "История мальчика, который " +
                "выжил и его друзей", 3, new MotionPictureAssociation(1,"G", "У фильма нет возрастных ограничений")));

        when(request.getServletPath()).thenReturn("/film/get");

        when(request.getParameter("id")).thenReturn("1");

        filmServlet.doGet(request, response);

        verify(request, times(1)).getServletPath();
    }
}