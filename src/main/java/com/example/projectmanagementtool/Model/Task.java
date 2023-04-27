package com.example.projectmanagementtool.Model;

import java.time.LocalDate;

public class Task implements Component{
    String name;
    String description;
    User owner;
    LocalDate deadline;

    double allocatedTime;

    public double getAllocatedTime() {
        return allocatedTime;
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
    public LocalDate getDeadline() {
        return deadline;
    }
}
