package com.example.projectmanagementtool.Model;

import java.time.LocalDate;

public class Task implements Component{
    String name;
    String description;
    User owner;
    double allocatedTime;
    LocalDate deadline;

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
