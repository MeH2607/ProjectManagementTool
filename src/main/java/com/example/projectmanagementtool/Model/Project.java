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
    private int ownerID; //Bruges til at få et id fra owner når man vælger en owner fra html formen,
    // da Thymeleaf har svært ved at arbejde
    private int timeSpent;
    private List<Subproject> subprojectList; // The project contains all its subprojects in this list

    public Project() {
        this.subprojectList = new ArrayList<Subproject>();
        this.timeSpent = 0;
    }

    public Project(int id, String name, String description, int allocatedTime, User owner, String deadline) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.allocatedTime = allocatedTime;
        this.owner = owner;
        this.deadline = LocalDate.parse(deadline);
        this.subprojectList = new ArrayList<Subproject>();
        this.timeSpent = 0;
    }

    public Project(int id, String name, String description, int allocatedTime, String deadline) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.allocatedTime = allocatedTime;
        this.owner = new User();
        this.deadline = LocalDate.parse(deadline);
        this.subprojectList = new ArrayList<Subproject>();
    }

    public Project(int id, String name, String description, int allocatedTime, String deadline, User owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.allocatedTime = allocatedTime;
        this.owner = new User();
        this.deadline = LocalDate.parse(deadline);
        this.owner = owner;
        this.subprojectList = new ArrayList<Subproject>();
    }

    public void addSubproject(Subproject newSubproject) {
        subprojectList.add(newSubproject);
    }

    public List<Subproject> getSubprojectList() {
        return subprojectList;
    }

    public void setSubprojectList(List<Subproject> subprojectList) {
        this.subprojectList = subprojectList;
    }

    public List<User> getProjectMembers() {
        // Finds 'projectmembers' by iterating all members in all subprojects
        List<User> projectMembers = new ArrayList<User>();
        // Itererating subprojects
        for (Subproject subproject : subprojectList) {
            // Itererating all users in all subprojects and adding to projectmembers
            for (User user : subproject.getSubprojectMembers()) {
                projectMembers.add(user);
            }
        }
        return projectMembers;
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

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
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

    @Override
    public String toString() {
        return "Project{" + "name='" + name + '\'' + ", description='" + description + '\'' + ", owner=" + owner + ", deadline=" + deadline + ", subprojectList=" + subprojectList + '}';
    }
}
