package com.example.projectmanagementtool.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Project implements Component{
    private String name;
    private String description;
    private User owner;
    private LocalDate deadline;

    private List<Subproject> subprojectList; // The project contains all its subprojects in this list

    public void addSubproject(Subproject newSubproject){
        subprojectList.add(newSubproject);
    }

    public List<Subproject> getSubprojectList() {
        return subprojectList;
    }


    public List<User> getProjectMembers() {
        // Finds 'projectmembers' by iterating all members in all subprojects
        List <User> projectMembers = new ArrayList<User>();
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

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", owner=" + owner +
                ", deadline=" + deadline +
                ", subprojectList=" + subprojectList +
                '}';
    }
}
