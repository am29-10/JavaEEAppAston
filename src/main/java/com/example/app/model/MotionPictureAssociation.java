package com.example.app.model;

public class MotionPictureAssociation {
    private int id;
    private String name;
    private String description;

    public MotionPictureAssociation(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public MotionPictureAssociation(String name, String description) {
        this.name = name;
        this.description = description;
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

    @Override
    public String toString() {
        return "MotionPictureAssociation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
