package com.example.projectmanagementtool.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task implements Component{
    private int id;
    private String name;
    private String description;
    private User owner;
    private LocalDate deadline;
    private double allocatedTime;
    private int subprojectID;
    private String status;
    private int ownerID;

    public Task(String name, String description, double allocatedTime, String deadline, int subprojectID, String status) {
        this.name = name;
        this.description = description;
        this.allocatedTime = allocatedTime;
        this.owner = new User();
        this.deadline = LocalDate.parse(deadline);
        this.subprojectID = subprojectID;
        this.status = status;
    }

    public Task(int id, String name, String description, double allocatedTime, String deadline, int subprojectID, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.allocatedTime = allocatedTime;
        this.owner = new User();
        this.deadline = LocalDate.parse(deadline);
        this.subprojectID = subprojectID;
        this.status = status;
    }

    // Converts localDate into formatted string for the DB
    public String getDeadlineAsString(){

        // Creating a DateTimeFormatter object with the desired format for the DB
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        //Converting localdate to string
        String deadlineAsString = deadline.format(formatter);

        return deadlineAsString;
    }


    public int getId() {
        return id;
    }

    public int getOwnerID(){
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
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
