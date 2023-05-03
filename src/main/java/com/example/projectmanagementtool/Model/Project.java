package com.example.projectmanagementtool.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Project implements Component {
    String name;
    String description;
    User owner;

    int ownerID; //Bruges til at få et id fra owner når man vælger en owner fra html formen,
                // da Thymeleaf har svært ved at arbejde med composite attributer.

    LocalDate deadline;

    List<Subproject> subprojectList; // The project contains all its subprojects in this list

    public void addSubproject(Subproject newSubproject) {
        subprojectList.add(newSubproject);
    }

    public List<Subproject> getSubprojectList() {
        return subprojectList;
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

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public User getOwner() {
        return owner;
    }

    @Override
    public double getAllocatedTime() {
        double allocatedTime = 0;
        // Itererating subprojects and adding together their Allocated time
        for (Subproject subproject : subprojectList) {
            allocatedTime = allocatedTime + subproject.getAllocatedTime();
        }
        return allocatedTime;
    }

    @Override
    public LocalDate getDeadline() {
        return deadline;
    }

    public void setSubprojectList(List<Subproject> subprojectList) {
        this.subprojectList = subprojectList;
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

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }
}
