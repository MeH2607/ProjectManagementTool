package com.example.projectmanagementtool.Model;

import java.time.LocalDate;
import java.util.List;

public class Subproject implements Component{
    String name;
    String description;
    User owner;
    LocalDate deadline;

    List<User> subprojectMembers;
    List<Task> taskList;

    public void addMember(User newUser){
        subprojectMembers.add(newUser);
    }

    public void addTask(Task newTask){
        taskList.add(newTask);
    }

    public List<User> getSubprojectMembers(){
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

    @Override
    public User getOwner() {
        return owner;
    }

    public double getAllocatedTime() {
        double allocatedTime = 0;
        // Itererating subprojects and adding together their Allocated time
        for (Task task : taskList) {
            allocatedTime = allocatedTime + task.getAllocatedTime();
        }
        return allocatedTime;
    }

    @Override
    public LocalDate getDeadline() {
        return deadline;
    }
}
