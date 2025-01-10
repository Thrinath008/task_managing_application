package org.example.collaborative_task_management_application.databases;

import com.almasb.fxgl.net.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Main_database_connection {
    private static final String URL = "jdbc:mysql://localhost:3306/taskmanager_final";
    private static final String USER = "root";
    private static final String PASSWORD = "CHATgpt@project";


    public static java.sql.Connection connectiondb(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            return (java.sql.Connection) DriverManager.getConnection(URL,USER,PASSWORD);

        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
            return null;
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            return  null;
        }
    }
    public static void main(String[] args) {
        java.sql.Connection connection = connectiondb();
        if (connection != null) {
            System.out.println("Connection successful!");
        } else {
            System.out.println("Failed to connect.");
        }
    }
}
