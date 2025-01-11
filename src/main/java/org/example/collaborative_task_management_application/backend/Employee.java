package org.example.collaborative_task_management_application.backend;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private int Id;
    private String name;
    private String email;
    private String role;
    private List<String> assignedProjects;

    public Employee(int id, String name, String email, String role, String s, String string) {
        this.Id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        List<Project> assignedProjects = new ArrayList<>();
    }


    public Employee(int id, String name, String email, String phone, String role, String s, int id1, String role1) {

        this.Id = id1;
        this.role = role1;
    }

    
}
