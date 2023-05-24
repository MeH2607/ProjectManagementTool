package com.example.projectmanagementtool.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Project implements Component {
    private int id;
    private String name;
    private String description;
    private int allocatedTime;
    private User owner;
    private LocalDate deadline;
    private int timeSpent;
    private double timeRemaining;
    private int daysUntilDeadline;
    private double allocatedTimeInWorkdays;
    private double hoursPerDayUntilDeadline;
    private List<Subproject> subprojectList; // The project contains all its subprojects in this list

    public Project() {
        this.timeSpent = 0;
    }

    public Project(int id, String name, String description, int allocatedTime, User owner, String deadline) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.allocatedTime = allocatedTime;
        this.owner = owner;
        this.deadline = LocalDate.parse(deadline);
        this.timeSpent = 0;
    }

    public Project(int id, String name, String description, int allocatedTime, String deadline) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.allocatedTime = allocatedTime;
        this.owner = new User();
        this.deadline = LocalDate.parse(deadline);
    }

    public Project(int id, String name, String description, int allocatedTime, String deadline, User owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.allocatedTime = allocatedTime;
        this.owner = new User();
        this.deadline = LocalDate.parse(deadline);
        this.owner = owner;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(int timeRemaining) {
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

    public void setAllocatedTimeInWorkdays(int allocatedTimeInWorkdays) {
        this.allocatedTimeInWorkdays = allocatedTimeInWorkdays;
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
    public int getAllocatedTime() {
        return allocatedTime;
    }

    public void setAllocatedTime(int allocatedTime) {
        this.allocatedTime = allocatedTime;
    }

    @Override
    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
    }

    public double getHoursPerDayUntilDeadline() {
        return hoursPerDayUntilDeadline;
    }
    public void setHoursPerDayUntilDeadline(double hoursPerDayUntilDeadline) {
        this.hoursPerDayUntilDeadline = hoursPerDayUntilDeadline;
    }

    @Override
    public String toString() {
        return "Project{" + "name='" + name + '\'' + ", description='" + description + '\'' + ", owner=" + owner + ", deadline=" + deadline;
    }
}
