package com.example.app.service;

import com.example.app.model.MotionPictureAssociation;
import com.example.app.storage.MpaDao;
import com.example.app.storage.impl.MpaDaoImpl;

import java.util.List;

public class MpaService {

    MpaDao mpaDao;

    public MpaService() {
        mpaDao = new MpaDaoImpl();
    }

    public MotionPictureAssociation create(MotionPictureAssociation mpa) {
        return mpaDao.create(mpa);
    }

    public List<MotionPictureAssociation> readAll() {
        return mpaDao.readAll();
    }

    public MotionPictureAssociation update(int id, MotionPictureAssociation mpa) {
        return mpaDao.update(id, mpa);
    }

    public MotionPictureAssociation getMpaById(int id) {
        return mpaDao.getMpaById(id);
    }
}
