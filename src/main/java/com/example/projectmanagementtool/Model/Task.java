package com.example.projectmanagementtool.Model;

import java.time.LocalDate;

public class Task implements Component{
    String name;
    String description;
    User owner;
    LocalDate deadline;

    double allocatedTime;

    public Task(String name, String description, double allocatedTime, int ownerID, String deadline, int subprojectID) {
        this.name = name;
        this.description = description;
        this.allocatedTime = allocatedTime;
        this.owner = new User(ownerID);
        this.deadline = LocalDate.parse(deadline);
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

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", owner=" + owner +
                ", deadline=" + deadline +
                ", allocatedTime=" + allocatedTime +
                '}';
    }
}
