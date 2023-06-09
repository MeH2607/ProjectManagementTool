package com.example.projectmanagementtool.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task implements Component {
    private int id;
    private String name;
    private String description;
    private User owner;
    private LocalDate deadline;
    private int allocatedTime;
    private int subprojectID;
    private String status;

    public Task(int id, String name, String description, int allocatedTime, User owner, String deadline, int subprojectID, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.allocatedTime = allocatedTime;
        this.owner = owner;
        this.deadline = LocalDate.parse(deadline);
        this.subprojectID = subprojectID;
        this.status = status;
    }

    public Task() {
    }

    // Converts localDate into formatted string for the DB
    public String getDeadlineAsString() {
        // Creating a DateTimeFormatter object with the desired format for the DB
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        //Converting localdate to string
        String deadlineAsString = deadline.format(formatter);
        return deadlineAsString;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int getAllocatedTime() {
        return allocatedTime;
    }

    public void setAllocatedTime(int allocatedTime) {
        this.allocatedTime = allocatedTime;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public int getSubprojectID() {
        return subprojectID;
    }

    public void setSubprojectID(int subprojectID) {
        this.subprojectID = subprojectID;
    }

    @Override
    public String toString() {
        return "Task{" + "name='" + name + '\'' + ", description='" + description + '\'' + ", owner=" + owner + ", deadline=" + deadline + ", allocatedTime=" + allocatedTime + ", subprojectID=" + subprojectID + ", status='" + status + '\'' + '}';
    }
}
