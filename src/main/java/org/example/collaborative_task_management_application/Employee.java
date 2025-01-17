package org.example.collaborative_task_management_application;

import org.example.collaborative_task_management_application.databases.Main_database_connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Employee {
    private Random rand = new Random();
    public int Id;
    public String name;
    public String email;
    public String role;
    public int projectId;
    public List<String> assignedProjects;
    private Set<Integer> assignedId = new HashSet<Integer>();

    public Employee(String name, String email, String role) throws SQLException {
        this.Id= rand.nextInt(1000);
        Connection connection = Main_database_connection.connectiondb();
        String sql = "SELECT employeeId FROM employee";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            ResultSet resultSet = pstmt.executeQuery();
            while(resultSet.next()){
                assignedId.add(resultSet.getInt("employeeId"));
                }
            }
        while(assignedId.contains(this.Id)){
            this.Id = rand.nextInt(1000);
        }
        this.name = name;
        this.email = email;
        this.role = role;
        List<Project> assignedProjects = new ArrayList<>();
    }

    public Employee(int id, String name) {
        this.Id = id;
        this.name = name;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public int getProjectId() {
        return projectId;
    }

//    public void addEmployee(int id, String name, String email, String role) {
//        Employee employee = new Employee(id, name, email, role, role, "");
//        try {
//            Connection connection = Main_database_connection.connectiondb();
//            Main_database_connection db = new Main_database_connection();
//            db.insertEmployee(id, name, email, role, employee);
//            db.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }


}
