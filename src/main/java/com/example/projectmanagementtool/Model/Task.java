package com.example.projectmanagementtool.Model;

import java.time.LocalDate;

public class Task implements Component{
    private String name;
    private String description;
    private User owner;
    private LocalDate deadline;
    private double allocatedTime;
    private int subprojectID;
    private String status;

    public Task(String name, String description, double allocatedTime, int ownerID, String deadline, int subprojectID, String status) {
        this.name = name;
        this.description = description;
        this.allocatedTime = allocatedTime;
        this.owner = new User();
        this.deadline = LocalDate.parse(deadline);
        this.subprojectID = subprojectID;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Task() {

    }

    @Override
    public double getAllocatedTime() {
        return allocatedTime;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public User getOwner() {
        return owner;
    }

    @Override
    public LocalDate getDeadline() {
        return deadline;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setAllocatedTime(double allocatedTime) {
        this.allocatedTime = allocatedTime;
    }

    public int getSubprojectID() {
        return subprojectID;
    }

    public void setSubprojectID(int subprojectID) {
        this.subprojectID = subprojectID;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", owner=" + owner +
                ", deadline=" + deadline +
                ", allocatedTime=" + allocatedTime +
                ", subprojectID=" + subprojectID +
                ", status='" + status + '\'' +
                '}';
    }
}
