package com.example.projectmanagementtool.Repository.TaskComparators;

import com.example.projectmanagementtool.Model.Task;

import java.util.Comparator;

public class NameComparator implements Comparator<Task> {

    public int compare(Task task1, Task task2){
        return task1.getName().compareTo(task2.getName());
    }
}
