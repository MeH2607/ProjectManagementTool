package com.example.projectmanagementtool.Repository.ProjectsComparators;

import com.example.projectmanagementtool.Model.Project;

import java.util.Comparator;

public class ProjectTimeComparator implements Comparator<Project> {

    public int compare(Project p1, Project p2){
        if(p1.getAllocatedTime() < p2.getAllocatedTime())
            return -1;
        else if(p1.getAllocatedTime() > p2.getAllocatedTime())
            return 1;
        else
            return 0;
    }
}