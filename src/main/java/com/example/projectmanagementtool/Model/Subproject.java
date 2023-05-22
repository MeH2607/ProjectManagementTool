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

    // getProjectMembers should iterate the subprojects tasks by each call
    public List<User> getSubprojectMembers() {
        List<User> subprojectMembers = new ArrayList<>();
        // TODO iterate all tasks in this subproject and add the members to the list before returning it
        return subprojectMembers;
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

    @Override
    public String toString() {
        return "Subproject{" + "name='" + name + '\'' + ", description='" + description + '\'' + ", allocatedTime=" + allocatedTime + ", Owner=" + owner.getName() + ", deadline='" + deadline + '\'' + ", projectID=" + projectID + id +

                '}';
    }
}
