package com.example.projectmanagementtool.Model;

public class Subproject implements Component{
    String name;
    String description;
    String owner;
    double allocatedTime;
    double deadline;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String owner() {
        return owner;
    }

    @Override
    public double allocatedTime() {
        return allocatedTime;
    }

    @Override
    public double deadline() {
        return deadline;
    }
}
