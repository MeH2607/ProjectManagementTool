package com.example.projectmanagementtool.Repository.TaskComparators;

import com.example.projectmanagementtool.Model.Task;

import java.util.Comparator;

public class TaskDeadlineComparator implements Comparator<Task> {

    public int compare(Task task1, Task task2){
        return task1.getDeadline().compareTo(task2.getDeadline());
    }
}