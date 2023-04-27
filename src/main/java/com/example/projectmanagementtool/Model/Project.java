package com.example.projectmanagementtool.Model;

import java.util.List;

public class Project implements Component{
    String name;
    String description;
    String owner;
    double allocatedTime;
    double deadline;
    List<Subproject> subprojectList;
    List<String> members;

    public void addMember(String nameAdd){
        members.add(nameAdd);
    }

    public List<Subproject> getSubprojectList() {
        return subprojectList;
    }

    public List<String> getMembers() {
        return members;
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
