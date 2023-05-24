package com.example.projectmanagementtool.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Subproject implements Component {
    private int id;
    private String name;
    private String description;
    private User owner;
    private int allocatedTime;
    private int timeSpent;
    private LocalDate deadline;
    private int projectID;
    private double timeRemaining;
    private int daysUntilDeadline;
    private double allocatedTimeInWorkdays;
    private double hoursPerDayUntilDeadline;

    public Subproject(int id, String name, String description, User owner, String deadline, int subprojectID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.allocatedTime = 0;
        this.timeSpent = 0;
        this.owner = owner;
        this.deadline = LocalDate.parse(deadline);
        this.projectID = subprojectID;
    }

    public Subproject() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public int getAllocatedTime() {
        return allocatedTime;
    }

    public void setAllocatedTime(int allocatedTime) {
        this.allocatedTime = allocatedTime;
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
    }

    @Override
    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public double getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(double timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public int getDaysUntilDeadline() {
        return daysUntilDeadline;
    }

    public void setDaysUntilDeadline(int daysUntilDeadline) {
        this.daysUntilDeadline = daysUntilDeadline;
    }

    public double getAllocatedTimeInWorkdays() {
        return allocatedTimeInWorkdays;
    }

    public void setAllocatedTimeInWorkdays(double allocatedTimeInWorkdays) {
        this.allocatedTimeInWorkdays = allocatedTimeInWorkdays;
    }

    public double getHoursPerDayUntilDeadline() {
        return hoursPerDayUntilDeadline;
    }

    public void setHoursPerDayUntilDeadline(double hoursPerDayUntilDeadline) {
        this.hoursPerDayUntilDeadline = hoursPerDayUntilDeadline;
    }

    @Override
    public String toString() {
        return "Subproject{" + "name='" + name + '\'' + ", description='" + description + '\'' + ", allocatedTime=" + allocatedTime + ", Owner=" + owner.getName() + ", deadline='" + deadline + '\'' + ", projectID=" + projectID + id +

                '}';
    }
}
