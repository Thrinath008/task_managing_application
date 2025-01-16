package org.example.collaborative_task_management_application;

import org.example.collaborative_task_management_application.databases.Main_database_connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Project {
    private final int projectId;
    private String name;
    private String description;
    private final Date startDate;
    private Date endDate;
    private final float budget;
    private String status;
    protected List<Integer> teamMembersId;
    protected List<String> tasks;

    public Project(int projectId, String status, float budget, Date endDate, String description, Date startDate, String name) {
        this.projectId = projectId;
        this.status = status;
        this.budget = budget;
        this.endDate = endDate;
        this.description = description;
        this.startDate = new Date();
        this.name = name;
        this.teamMembersId = new ArrayList<Integer>();
        this.tasks = new ArrayList<String>();
    }

    public void createProject(int projectId, String status, float budget, Date endDate, String description, Date startDate, String name){
        Project project = new Project(projectId,status,budget,endDate,description,startDate,name);
        try{
            Main_database_connection db = new Main_database_connection();
            db.insertProject(projectId,name,description, (java.sql.Date) startDate, (java.sql.Date) endDate,budget,status,project);
            db.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProjectDetails(int projectId, String description, Date endDate, String status, String name){
        try{
            Main_database_connection db = new Main_database_connection();
            Project pr=db.selectParticularProject(projectId);
            pr.setDescription(description);
            pr.setEndDate(endDate);
            pr.setName(name);
            pr.setStatus(status);
            db.updateProject(projectId,description, (java.sql.Date) endDate,status,pr,name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void assignTeamMember(int projectId,int teamMemberId){

        try{
            Main_database_connection db = new Main_database_connection();
            Project project = db.selectParticularProject(projectId);
            project.teamMembersId.add(teamMemberId);
            db.updateProjectJSON(projectId,project);
            db.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeTeamMember(int projectId,int teamMemberId){

        try{
            Main_database_connection db = new Main_database_connection();
            Project project = db.selectParticularProject(projectId);
            project.teamMembersId.remove(teamMemberId);
            db.updateProjectJSON(projectId,project);
            db.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeProject(int projectId){
        try{
            Main_database_connection db = new Main_database_connection();
            db.deleteProject(projectId);
            db.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
