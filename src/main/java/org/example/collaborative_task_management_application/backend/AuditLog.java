package org.example.collaborative_task_management_application.backend;

import java.util.Date;

public class AuditLog {
    private int logId;
    private String action;
    private Date timestamp;
    private int performedBy;

    public AuditLog(int logId, String action, Date timestamp, int performedBy) {
        this.logId = logId;
        this.action = action;
        this.timestamp = timestamp;
        this.performedBy = performedBy;
    }
    public void createLog(){

    }
    public void deleteLog(){

    }
    public void getAuditLog(){

    }
}
