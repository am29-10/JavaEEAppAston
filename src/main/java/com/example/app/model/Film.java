package com.example.app.model;

import java.util.List;

public class Film {
    private int id;
    private String name;
    private String description;
    private int duration;
    private MotionPictureAssociation mpa;
    private List<Genre> genres;

    public Film(int id, String name, String description, int duration, MotionPictureAssociation mpa) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.mpa = mpa;
    }

    public Film(String name, String description, int duration, MotionPictureAssociation mpa) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.mpa = mpa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getDuration() {
        return duration;
    }

    public MotionPictureAssociation getMpa() {
        return mpa;
    }

    public void setMpa(MotionPictureAssociation mpa) {
        this.mpa = mpa;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", mpa=" + mpa +
                ", genres=" + genres +
                '}';
    }
}
