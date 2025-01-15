package org.example.collaborative_task_management_application.backend;

public class login {
    private String Id;
    private String password;
    private String role;

    public login(String id, String password, String role) {
        Id = id;
        this.password = password;
        this.role = role;
    }
    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return Id;
    }
}
