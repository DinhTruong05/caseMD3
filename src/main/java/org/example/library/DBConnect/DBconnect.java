package org.example.library.DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnect {
    private final String jdbcUrl  = "jdbc:mysql://localhost:3306/librarymanager?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private final String username = "root";
    private final String password = "Dinhtruong95";

    public DBconnect() {

    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("connect success");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return connection;
    }
}
