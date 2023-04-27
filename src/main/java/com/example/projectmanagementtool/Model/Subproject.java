package com.example.projectmanagementtool.Model;

import java.time.LocalDate;
import java.util.List;

public class Subproject implements Component{
    String name;
    String description;
    User owner;
    double allocatedTime;
    LocalDate deadline;

    List<User> subprojectMembers;

    public void addMember(User userAdd){
        subprojectMembers.add(userAdd);
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
    public User owner() {
        return owner;
    }

    @Override
    public double allocatedTime() {
        return allocatedTime;
    }

    @Override
    public LocalDate deadline() {
        return deadline;
    }
}
