package org.example.collaborative_task_management_application.databases;

import com.google.gson.GsonBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.collaborative_task_management_application.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;

public class Main_database_connection {
    private static final String URL = "jdbc:mysql://localhost:3306/taskmanager_final";
    private static final String USER = "root";
    private static final String PASSWORD = "CHATgpt@project";
    private static Connection connection;

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

    public Main_database_connection() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Add employee method
    public static boolean addEmployee(String name, String email, String password) {
        String query = "INSERT INTO employee (name, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connectiondb().prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected >0){
                logAction("add employee","employee added with email"+email);
            }
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

            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    logAction("login","user "+name+" logged in successfully");
                    System.out.println("Login successful! Welcome, " + name + "!");
                    return true;
                } else {
                    logAction("login attempt","failed login attemt for use user: "+name);
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

    public static void logAction(String actionType, String description){
        String query = "INSERT INTO activity_log (action_type, description) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connectiondb().prepareStatement(query)){
            preparedStatement.setString(1,actionType);
            preparedStatement.setString(2,description);
            preparedStatement.executeUpdate();
            System.out.println("action logged: "+actionType+"- "+description);
        } catch (SQLException e) {
            System.out.println("error while loged action "+e.getMessage());
        }
    }
    public static ObservableList<LogEntry> getLogEntry(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        ObservableList<LogEntry> data = FXCollections.observableArrayList();
        try (Statement statement = connectiondb().createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM activity_log");

            while (resultSet.next()){
                String formattedTimestamp = resultSet.getTimestamp("timestamp").toLocalDateTime().format(formatter);
                data.add(new LogEntry(resultSet.getString("action_type"),
                        resultSet.getString("description"),formattedTimestamp));
            }
            connectiondb().close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;

    }
    public static void deleteLog() throws SQLException {
        String quary = "TRUNCATE TABLE activity_log";
        try(PreparedStatement preparedStatement = connectiondb().prepareStatement(quary)){
            preparedStatement.executeUpdate();
            System.out.println("all the logs have been deleted successfully!!!!");
        }
    }

    public static boolean updateEmployee(int employeeId, String name, String email, String password) {
        String query = "UPDATE employee SET name = ?, email = ?, password = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connectiondb().prepareStatement(query)) {
            // Set the new values for the employee
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setInt(4, employeeId); // The employee ID to identify which row to update

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                logAction("update employee", "Employee updated with ID: " + employeeId);
                System.out.println("Employee updated successfully!");
                return true;
            } else {
                System.out.println("No employee found with the given ID: " + employeeId);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error while updating employee: " + e.getMessage());
            return false;
        }
    }
    public static void saveTasks(String column, List<String> tasks){
        String deleteQuery = "delete from tasks where column_name = ?";
        String insertQuery = "insert into tasks(column_name,task_name) values(?,?)";
        try (PreparedStatement preparedStatement = connectiondb().prepareStatement(deleteQuery);
        PreparedStatement preparedStatement1 = connectiondb().prepareStatement(insertQuery)){
            preparedStatement.setString(1,column);
            preparedStatement.executeUpdate();

            for (String task : tasks){
                preparedStatement1.setString(1,column);
                preparedStatement1.setString(2,task);
                preparedStatement1.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<String> getTasks(String column){
        String qurey = "select task_name from tasks where column_name = ?";
        List<String> tasks = new ArrayList<>();
        try (PreparedStatement preparedStatement = connectiondb().prepareStatement(qurey)){
            preparedStatement.setString(1,column);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                tasks.add(resultSet.getString("task_name"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tasks;
    }


    public static void main(String[] args) {
        // Test the connection and addEmployee method
        connectiondb();
        close();
    }

    public static boolean insertEmployee(String name, String email, String role, Employee employee, String password) throws SQLException {
        String sql = "INSERT INTO employee (employeeId, name, email, role, employeeJson, password) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            Gson gson = new GsonBuilder().excludeFieldsWithModifiers(java.lang.reflect.Modifier.PRIVATE).create();
            String employeeJson = gson.toJson(employee);
            pstmt.setInt(1, employee.getId());
            pstmt.setString(2, name);
            pstmt.setString(3, email);
            pstmt.setString(4, role);
            pstmt.setString(5, employeeJson);
            pstmt.setString(6,password);
            pstmt.executeUpdate();
            Employee empTest = gson.fromJson(employeeJson,Employee.class);
            System.out.println(empTest.getName());
            logAction("sign up",employee.getId()+" "+employee.getName()+" added user to database");
            return true;
        }
    }

    public static Employee getEmployeeByName(String username,String password){
        String sql = "select employeeJson from employee where name = ? and password = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1,username);
            pstmt.setString(2, password);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                Gson gson = new GsonBuilder().excludeFieldsWithModifiers(java.lang.reflect.Modifier.PRIVATE).create();
                return gson.fromJson(resultSet.getString("employeeJson"), Employee.class);
            } else {
                System.out.println("No employee found with the given credentials.");
                return null; // No matching employee
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Employee getEmployeeByID(int id){
        String sql = "select employeeJson from employee where employeeId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1,id);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                Gson gson = new GsonBuilder().excludeFieldsWithModifiers(java.lang.reflect.Modifier.PRIVATE).create();
                return gson.fromJson(resultSet.getString("employeeJson"), Employee.class);
            } else {
                System.out.println("No employee found with the given credentials.");
                return null; // No matching employee
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ObservableList<Employee> selectParticularEmployee(){
        String sql = "SELECT * FROM employee";
        ObservableList<Employee> empLiast = FXCollections.observableArrayList();
        try(Statement statement = connectiondb().createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                empLiast.add(new Employee(resultSet.getInt("employeeId"),resultSet.getString("name")));
            }
//            connectiondb().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return empLiast;
    }
    public static ObservableList<Task> getTasksForTabel() throws SQLException {
        String sql = "select * from tasks";
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        try (Statement statement = connectiondb().createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                tasks.add(new Task(resultSet.getInt("id"),resultSet.getString("task_name"), resultSet.getInt("assignedEmployeeId")));
            }
        }
        return tasks;
    }

    //#######################################Project Methods################################
    public void insertProject(int id, String name, String description, Date startDate, Date endDate, float budget, String status, Project project) throws SQLException {
        String sql = "INSERT INTO project (projectId, description, startDate, endDate, budget, status, projectJSON, name) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            Gson gson = new GsonBuilder().excludeFieldsWithModifiers(java.lang.reflect.Modifier.PRIVATE).create();
            String projectJSON = gson.toJson(project);
            pstmt.setInt(1, id);
            pstmt.setString(2, description);
            pstmt.setDate(3, startDate);
            pstmt.setDate(4, endDate);
            pstmt.setFloat(5, budget);
            pstmt.setString(6, status);
            pstmt.setString(7, projectJSON);
            pstmt.setString(8, name);
            pstmt.executeUpdate();
        }
    }

    public ResultSet selectAllProjects() throws SQLException {
        String sql = "SELECT * FROM project";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(sql);
    }

    public Project selectParticularProject(int projectId){
        String sql = "SELECT FROM project WHERE projectId =?";
        try{
            Gson gson = new GsonBuilder().excludeFieldsWithModifiers(java.lang.reflect.Modifier.PRIVATE).create();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,projectId);
            ResultSet resultSet = pstmt.executeQuery();
            return gson.fromJson(resultSet.getString("projectJSON"), Project.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProject(int projectId, String description, Date endDate, String status, Project project, String name) throws SQLException {
        String sql = "UPDATE project SET description = ?, endDate = ?, status = ?, projectJSON = ?, name =? WHERE projectId = ?";
        try {
            Gson gson = new GsonBuilder().excludeFieldsWithModifiers(java.lang.reflect.Modifier.PRIVATE).create();
            String projectJson = gson.toJson(project);
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, description);
            pstmt.setDate(2,endDate);
            pstmt.setString(3, status);
            pstmt.setString(4, projectJson);
            pstmt.setString(5, name);
            pstmt.setInt(6, projectId);
            pstmt.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateProjectJSON(int projectId,Project project) throws SQLException {
        String sql = "UPDATE project SET projectJSON = ? WHERE projectId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            Gson gson = new GsonBuilder().excludeFieldsWithModifiers(java.lang.reflect.Modifier.PRIVATE).create();
            String projectJson = gson.toJson(project);
            pstmt.setString(1, projectJson);
            pstmt.setInt(2, projectId);
            pstmt.executeUpdate();
        }
    }

    public void deleteProject(int projectId) throws SQLException {
        String sql = "DELETE FROM Project WHERE projectId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            pstmt.executeUpdate();
        }
    }


    // ============================== TASK METHODS ===============================
    public void insertTask(int taskId, String task_name, int assignedEmployeeId, Task task) throws SQLException {
        String sql = "INSERT INTO tasks (id, column_name, task_name, assignedEmployeeId, taskJSON) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            Gson gson = new GsonBuilder().excludeFieldsWithModifiers(java.lang.reflect.Modifier.PRIVATE).create();
            String taskJSON = gson.toJson(task);
            String column_name = "To-Do";
            pstmt.setInt(1, taskId);
            pstmt.setString(2, column_name);
            pstmt.setString(3, task_name);
            pstmt.setInt(4, assignedEmployeeId);
            pstmt.setString(5, taskJSON);
            pstmt.executeUpdate();
            logAction("Task Added", task.getTitle() + " with task id " + task.getTaskId()+ " added ");
        }
    }

    public ResultSet selectTasks() throws SQLException {
        String sql = "SELECT * FROM tasks";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(sql);
    }

    public void updateTaskStatus(int taskId, String newStatus, Task task) throws SQLException {
        String sql = "UPDATE tasks SET column_name = ?, taskJSON = ?  WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            Gson gson = new GsonBuilder().excludeFieldsWithModifiers(java.lang.reflect.Modifier.PRIVATE).create();
            String taskJSON = gson.toJson(task);
            pstmt.setString(1, newStatus);
            pstmt.setString(2, taskJSON);
            pstmt.setInt(3, taskId);
            pstmt.executeUpdate();
            logAction("upDated","task has changed to "+newStatus);
        }
    }

    public void updateTaskDetails(int taskId, String title, int assignedEmployeeId, Task task) {
        String sql = "UPDATE tasks SET task_name = ?, assignedEmployeeId = ?, taskJSON = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            Gson gson = new GsonBuilder().excludeFieldsWithModifiers(java.lang.reflect.Modifier.PRIVATE).create();;
            String taskJSON = gson.toJson(task);
            pstmt.setString(1, title);
            pstmt.setInt(2, assignedEmployeeId);
            pstmt.setString(3, taskJSON);
            pstmt.setInt(4, taskId);
            pstmt.executeUpdate();
            logAction("upDated","task has changed to "+title+" by "+assignedEmployeeId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTask(int taskId) throws SQLException {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, taskId);
            pstmt.executeUpdate();
        }
    }

    public Task getTaskJson(int taskId) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        try {
            Gson gson = new GsonBuilder().excludeFieldsWithModifiers(java.lang.reflect.Modifier.PRIVATE).create();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,taskId);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                // Retrieve the JSON string from the "taskJSON" column
                String taskJson = resultSet.getString("taskJSON");

                // Convert the JSON string to a Task object and return it
                return gson.fromJson(taskJson, Task.class);
            } else {
                // Handle case where no task is found for the given ID
                throw new RuntimeException("No task found with ID: " + taskId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //######################Messages methods####################################
    public void sendMessage(messages msg) throws SQLException {
        String sql = "INSERT INTO employeemessages (message_id, sender_id, receiver_id, message, message_json, sent_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            Gson gson = new GsonBuilder().excludeFieldsWithModifiers(java.lang.reflect.Modifier.PRIVATE).create();
            String messageJSON = gson.toJson(msg);
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            msg.setSentAt(LocalDateTime.now().format(formatter));
            pstmt.setInt(1, msg.getMessageId());
            pstmt.setInt(2, msg.getSenderId());
            pstmt.setInt(3, msg.getReceiverId());
            pstmt.setString(4, msg.getMessage());
            pstmt.setString(5, messageJSON);
            pstmt.setString(6, msg.getSentAt());
            pstmt.executeUpdate();
            logAction("Message sent", msg.getMessage() + " with message id " + msg.getMessageId()+ " added ");
        }
    }

    public List<messages> getMessageJson(int senderId, int receiverId) throws SQLException {
        String sql = "SELECT * FROM employeemessages WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?) ORDER BY sent_at ASC";
        System.out.println(senderId);
        System.out.println(receiverId);
        List<messages> msgList = new ArrayList<messages>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            Gson gson = new GsonBuilder().excludeFieldsWithModifiers(java.lang.reflect.Modifier.PRIVATE).create();
            pstmt.setInt(1, senderId);
            pstmt.setInt(2, receiverId);
            pstmt.setInt(3, receiverId);
            pstmt.setInt(4, senderId);
            ResultSet resultSet = pstmt.executeQuery();
            while(resultSet.next()){
                msgList.add(gson.fromJson(resultSet.getString("message_json"), messages.class));
            }
            System.out.println("Message size imported: " + msgList.size());
        }
        return msgList;
    }


}
