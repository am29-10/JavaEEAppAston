package com.example.app.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    private static final String DB = "db.properties";
    private String JDBC_URL;
    private String USERNAME;
    private String PASSWORD;
    private String CLASS_NAME;

    public Connector() {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(DB)) {
            properties.load(inputStream);
            JDBC_URL = properties.getProperty("postgres.url");
            USERNAME = properties.getProperty("postgres.username");
            PASSWORD = properties.getProperty("postgres.password");
            CLASS_NAME = properties.getProperty("postgres.driver-class-name");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Connector(String nameDB) {
        if (nameDB.equals("h2")) {
            Properties properties = new Properties();
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(DB)) {
                properties.load(inputStream);
                JDBC_URL = properties.getProperty("h2.url");
                USERNAME = properties.getProperty("h2.username");
                PASSWORD = properties.getProperty("h2.password");
                CLASS_NAME = properties.getProperty("h2.driver-class-name");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
