package com.example.projectmanagementtool.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Subproject implements Component{
    String name;
    String description;
    User owner;
    LocalDate deadline;

    List<Task> taskList;


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

    @Override
    public User getOwner() {
        return owner;
    }

    @Override
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
