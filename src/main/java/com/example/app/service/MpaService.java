package com.example.app.service;

import com.example.app.model.Mpa;
import com.example.app.storage.MpaDao;
import com.example.app.storage.impl.MpaDaoImpl;

import java.util.List;

public class MpaService {

    MpaDao mpaDao;

    public MpaService() {
        mpaDao = new MpaDaoImpl();
    }

    public Mpa create(Mpa mpa) {
        return mpaDao.create(mpa);
    }

    public List<Mpa> readAll() {
        return mpaDao.readAll();
    }

    public Mpa update(int id, Mpa mpa) {
        return mpaDao.update(id, mpa);
    }

    public Mpa getMpaById(int id) {
        return mpaDao.getMpaById(id);
    }
}
