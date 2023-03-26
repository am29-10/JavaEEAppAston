package com.example.app.storage;

import com.example.app.model.Mpa;

import java.util.List;

public interface MpaDao {
    Mpa create(Mpa mpa);

    List<Mpa> readAll();

    Mpa update(int id, Mpa mpa);

    Mpa getMpaById(int id);

}
