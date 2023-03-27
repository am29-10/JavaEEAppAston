package com.example.app.controller;

import com.example.app.model.Mpa;
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

class MpaServletTest {
    private MpaDao mpaDao;
    private MpaServlet mpaServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter writer;

    @BeforeEach
    public void beforeEach() throws IOException {
        mpaDao = mock(MpaDao.class);
        mpaServlet = new MpaServlet(mpaDao);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        writer = new StringWriter();

        when(response.getWriter()).thenReturn(new PrintWriter(writer));
    }

    @Test
    void getAllMpa() {
        List<Mpa> mpaList = new ArrayList<>();
        mpaList.add(new Mpa("G", "У фильма нет возрастных ограничений"));
        mpaList.add(new Mpa("PG", "Детям рекомендуется смотреть фильм с родителями"));
        mpaList.add(new Mpa("PG-13", "Детям до 13 лет просмотр не желателен"));
        mpaList.add(new Mpa("R", "Лицам до 17 лет просматривать фильм можно только в присутствии взрослого"));
        mpaList.add(new Mpa("NC-17777", "Лицам до 18 лет просмотр запрещён"));

        when(mpaDao.readAll()).thenReturn(mpaList);

        when(request.getServletPath()).thenReturn("/mpa/get-all");

        mpaServlet.doGet(request, response);

        verify(request, times(1)).getServletPath();
    }

    @Test
    void addMpa() {
        when(request.getServletPath()).thenReturn("/mpa/add");

        when(request.getParameter("name")).thenReturn("X");
        when(request.getParameter("description")).thenReturn("На сеанс не допускаются лица, не достигшие " +
                "17-летнего возраста");

        mpaServlet.doGet(request, response);

        Assertions.assertEquals("MPA с названием X добавлен в БД\r\n", writer.toString());
    }

    @Test
    void updateMpa() {
        when(request.getServletPath()).thenReturn("/mpa/update");

        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("X");
        when(request.getParameter("description")).thenReturn("На сеанс не допускаются лица, не достигшие " +
                "17-летнего возраста");

        mpaServlet.doGet(request, response);

        Assertions.assertEquals("MPA для id = 1 обновлено\r\n", writer.toString());
    }

    @Test
    void getMpa() {
        when(mpaDao.getMpaById(1)).thenReturn(new Mpa(1,"G", "У фильма нет возрастных ограничений"));
        when(request.getServletPath()).thenReturn("/mpa/get");

        when(request.getParameter("id")).thenReturn("1");

        mpaServlet.doGet(request, response);

        verify(request, times(1)).getServletPath();
    }
}