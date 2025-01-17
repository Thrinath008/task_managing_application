package org.example.collaborative_task_management_application;

import org.example.collaborative_task_management_application.databases.Main_database_connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Task {
    private Random rand = new Random();
    public int taskId;
    public String title;
    public String description;
    public String priority;
    public String status;
    public Date dueDate;
    public int assignedEmployeeId;
    public Set<Integer> assignedId = new HashSet<Integer>();

    public Task(String title) throws SQLException {
        this.taskId= rand.nextInt(1000);
        Connection connection = Main_database_connection.connectiondb();
        String sql = "SELECT id FROM tasks";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            ResultSet resultSet = pstmt.executeQuery();
            while(resultSet.next()){
                assignedId.add(resultSet.getInt("id"));
            }
        }
        while(assignedId.contains(this.taskId)){
            this.taskId = rand.nextInt(1000);
        }
        this.assignedEmployeeId = 0;
        this.title = title;
        System.out.println("Task name: "+this.title+" Id: "+this.taskId);
    }

    public void createTask(Task task) {
        try {
            Main_database_connection db = new Main_database_connection();
            db.insertTask(task.getTaskId(), task.getTitle(), task.getAssignedEmployeeId(), task);
            db.close();
        } catch (Exception e) {
            throw new RuntimeException("Error creating task", e);
        }
    }

    public String getTitle() {
        return title;
    }

    public int getAssignedEmployeeId() {
        return assignedEmployeeId;
    }

    public int getTaskId() {
        return taskId;
    }

    public static void updateTaskStatus(int taskId, String column_name) {
        try {
            Main_database_connection db = new Main_database_connection();
            Task task = db.getTaskJson(taskId);
            task.setStatus(column_name);
            db.updateTaskStatus(taskId, column_name, task);
            db.close();
        } catch (Exception e) {
            throw new RuntimeException("Error updating task", e);
        }
    }

    public void updateTaskDetails(int taskId, int assignedEmployeeId, String title) {
        try {
            Main_database_connection db = new Main_database_connection();
            Task task = db.getTaskJson(taskId);
            task.setAssignedEmployeeId(assignedEmployeeId);
            task.setTitle(title);
            db.updateTaskDetails(taskId,title,assignedEmployeeId,task);
            db.close();
        } catch (Exception e) {
            throw new RuntimeException("Error updating task", e);
        }
    }
    public Task(int id,String title,int assignedEmployeeId){
        this.taskId = id;
        this.title = title;
        this.assignedEmployeeId = assignedEmployeeId;
    }

    public void assignEmployee(int employeeId) {
        this.assignedEmployeeId = employeeId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setAssignedEmployeeId(int assignedEmployeeId) {
        this.assignedEmployeeId = assignedEmployeeId;
    }


}
