package com.example.projectmanagementtool.Repository.ProjectsComparators;

import com.example.projectmanagementtool.Model.Project;
import com.example.projectmanagementtool.Model.Task;

import java.util.Comparator;

public class ProjectOwnerComparator implements Comparator<Project> {

    public int compare(Project p1, Project p2){
        return p1.getOwner().getName().compareTo(p2.getOwner().getName());
    }
}
