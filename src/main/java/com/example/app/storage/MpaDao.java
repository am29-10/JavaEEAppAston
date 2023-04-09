package com.example.app.storage;

import com.example.app.model.MotionPictureAssociation;

import java.util.List;

public interface MpaDao {
    MotionPictureAssociation create(MotionPictureAssociation mpa);

    List<MotionPictureAssociation> readAll();

    MotionPictureAssociation update(int id, MotionPictureAssociation mpa);

    MotionPictureAssociation getMpaById(int id);

}
