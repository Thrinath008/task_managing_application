package org.example.collaborative_task_management_application;

import org.example.collaborative_task_management_application.backend.Project;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    public int Id;
    public String name;
    public String email;
    public String role;
    public int projectId;
    public List<String> assignedProjects;

    public Employee(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
        List<Project> assignedProjects = new ArrayList<>();
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
