package org.example.collaborative_task_management_application;

public class LogEntry {
    private String actionType;
    private String description;
    private String timestamp;

    public LogEntry(String actionType, String description, String timestamp) {
        this.actionType = actionType;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getActionType() {
        return actionType;
    }

    public String getDescription() {
        return description;
    }

    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
}
