package com.example.projectmanagementtool.Repository.ProjectsComparators;

import com.example.projectmanagementtool.Model.Project;


import java.util.Comparator;

public class ProjectNameComparator implements Comparator<Project> {

    public int compare(Project p1, Project p2){
        return p1.getName().compareTo(p2.getName());
    }
}