package com.example.app.model;

public class Mpa {
    private int id;
    private String name;
    private String description;

    public Mpa(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Mpa(String name, String description) {
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
        return "Mpa{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
