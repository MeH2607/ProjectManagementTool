package com.example.projectmanagementtool.Repository.ProjectsComparators;

import com.example.projectmanagementtool.Model.Project;

import java.util.Comparator;

public class ProjectDeadlineComparator implements Comparator<Project> {

    public int compare(Project p1, Project p2){
        return p1.getDeadline().compareTo(p2.getDeadline());
    }
}
