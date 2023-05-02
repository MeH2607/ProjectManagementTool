package com.example.projectmanagementtool.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Subproject implements Component{
    private int id;
    private String name;
    private String description;
    private int ownerID;
    private double allocatedTime;
    private LocalDate deadline;
    private List<Task> taskList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public void setAllocatedTime(double allocatedTime) {
        this.allocatedTime = allocatedTime;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    private int projectID;

    public Subproject(int id, String name, String description, double allocatedTime, int ownerID, String deadline, int subprojectID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.allocatedTime = allocatedTime;
        this.ownerID = ownerID;
        this.deadline = LocalDate.parse(deadline);
        this.projectID = subprojectID;
    }

    public Subproject() {
    }





    public void addTask(Task newTask){
        taskList.add(newTask);
    }

    // getProjectMembers should iterate the subprojects tasks by each call
    public List<User> getSubprojectMembers(){
        List <User> subprojectMembers = new ArrayList<>();
        // TODO iterate all tasks in this subproject and add the members to the list before returning it
        return subprojectMembers;
    }

    public List<Task> getTasks(){
        return taskList;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public User getOwner() {
        return null;
    }



    @Override
    public double getAllocatedTime() {
        return allocatedTime;
    }

    @Override
    public LocalDate getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        return "Subproject{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", allocatedTime=" + allocatedTime +
                ", ownerID=" + ownerID +
                ", deadline='" + deadline + '\'' +
                ", projectID=" + projectID + id +

                '}';
    }
}
