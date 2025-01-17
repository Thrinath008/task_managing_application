package org.example.collaborative_task_management_application;

import org.example.collaborative_task_management_application.databases.Main_database_connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class messages {
    private Random rand = new Random();
    public int messageId;
    public int senderId;
    public int receiverId;
    public String message;
    public String sentAt;
    public Set<Integer> assignedId = new HashSet<Integer>();

    public messages(int senderId, int receiverId, String message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.messageId= rand.nextInt(1000);
        Connection connection = Main_database_connection.connectiondb();
        String sql = "SELECT id FROM tasks";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            ResultSet resultSet = pstmt.executeQuery();
            while(resultSet.next()){
                assignedId.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        while(assignedId.contains(this.messageId)){
            this.messageId = rand.nextInt(1000);
        }
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.sentAt = LocalDateTime.now().format(formatter);
    }

    public void createMessage(messages msg) throws SQLException {
        Main_database_connection db = new Main_database_connection();
        db.sendMessage(msg);
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSentAt() {
        return sentAt;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }
}

