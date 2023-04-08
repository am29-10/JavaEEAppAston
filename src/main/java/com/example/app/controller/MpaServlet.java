package com.example.app.controller;

import com.example.app.model.Mpa;
import com.example.app.service.MpaService;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/mpa/add", "/mpa/update", "/mpa/get-all", "/mpa/get"})
public class MpaServlet extends HttpServlet {
    private MpaService mpaService;

    public MpaServlet(MpaService mpaService) {
        this.mpaService = mpaService;
    }

    @Override
    public void init() {
        mpaService = new MpaService();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getServletPath();

        switch (action) {
            case "/mpa/add":
                addMpa(request, response);
                break;
            case "/mpa/update":
                updateMpa(request, response);
                break;
            case "/mpa/get":
                getMpa(request, response);
                break;
            default:
                getAllMpa(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    private void getAllMpa(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            List<Mpa> mpaList = mpaService.readAll();
            PrintWriter pw = response.getWriter();
            for (Mpa mpa : mpaList) {
                pw.println(mpa.getName());
            }
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addMpa(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            Mpa newMpa = new Mpa(name, description);
            mpaService.create(newMpa);
            pw.println("MPA с названием " + newMpa.getName() + " добавлен в БД");
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateMpa(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            Mpa updatedMpa = new Mpa(id, name, description);
            mpaService.update(id, updatedMpa);
            pw.println("MPA для id = " + id + " обновлено");
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getMpa(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            int id = Integer.parseInt(request.getParameter("id"));
            Mpa mpa = mpaService.getMpaById(id);
            PrintWriter pw = response.getWriter();
            pw.println(mpa.getName());
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
