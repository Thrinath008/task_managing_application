package org.example.collaborative_task_management_application.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main_database_connection {
    private static final String URL = "jdbc:mysql://localhost:3306/taskmanager_final";
    private static final String USER = "root";
    private static final String PASSWORD = "CHATgpt@project";
    private static Connection connection;

    // Establish the database connection
    public static Connection connectiondb() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connected successfully!");
            } catch (ClassNotFoundException e) {
                System.out.println("JDBC Driver not found: " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("Error connecting to the database: " + e.getMessage());
            }
        }
        return connection;
    }

    // Add employee method
    public static boolean addEmployee(String name, String email, String password) {
        String query = "INSERT INTO employee (name, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connectiondb().prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Employee added successfully!");
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error while adding employee: " + e.getMessage());
            return false;
        }
    }
    // Login employee method
    public static boolean loginEmployee(String name, String password) {
        // Query to check if the name and password match in the database
        String query = "SELECT * FROM employee WHERE name = ? AND password = ?";
        try (PreparedStatement preparedStatement = connectiondb().prepareStatement(query)) {
            preparedStatement.setString(1, name); // Set name
            preparedStatement.setString(2, password); // Set password

            // Execute the query
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Login successful! Welcome, " + name + "!");
                    return true;
                } else {
                    System.out.println("Invalid name or password. Please try again.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
            return false;
        }
    }


    // Close the database connection
    public static void close() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Database connection closed successfully!");
            } catch (SQLException e) {
                System.out.println("Error closing the database connection: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        // Test the connection and addEmployee method
        connectiondb();
        close();
    }
}
