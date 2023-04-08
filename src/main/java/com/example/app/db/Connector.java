package com.example.app.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private String JDBC_URL;
    private String USERNAME;
    private String PASSWORD;
    private String CLASS_NAME;

    public Connector() {
        JDBC_URL = "jdbc:postgresql://localhost:5432/filmsDB";
        USERNAME = "admin";
        PASSWORD = "admin";
        CLASS_NAME = "org.postgresql.Driver";
    }

    public Connector(String nameDB) {
        if (nameDB.equals("h2")) {
            JDBC_URL = "jdbc:h2:./db/films;DB_CLOSE_DELAY=-1";
            USERNAME = "sa";
            PASSWORD = "password";
            CLASS_NAME = "org.h2.Driver";
        }
    }

    public Connection getConnection() {
        Connection connection;
        try {
            Class.forName(CLASS_NAME);
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public void setJDBC_URL(String JDBC_URL) {
        this.JDBC_URL = JDBC_URL;
    }
}
