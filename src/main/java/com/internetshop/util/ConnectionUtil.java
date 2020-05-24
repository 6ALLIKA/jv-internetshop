package com.internetshop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;

public class ConnectionUtil {
    private static final Logger LOGGER = Logger.getLogger(ConnectionUtil.class);
    private static final String USERNAME = "b052e22caeead8";
    private static final String PASSWORD = "aca7e093";
    private static final String DBNAME = "heroku_8702cf8f07496a4";

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't find MySQL Driver", e);
        }
    }

    public static Connection getConnectionInternetShop() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", USERNAME);
        dbProperties.put("password", PASSWORD);

        String url = "jdbc:mysql://localhost:3306/" + DBNAME + "?serverTimezone=UTC";
        try {
            Connection connection = DriverManager.getConnection(url, dbProperties);
            LOGGER.info("Connection to DB established");
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Cant establish connection to our DB", e);
        }
    }
}
