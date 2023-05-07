package com.example.projectmanagementtool.Repository.TaskComparators;

import com.example.projectmanagementtool.Model.Task;

import java.util.Comparator;

public class TimeComparator implements Comparator<Task> {

    public int compare(Task task1, Task task2){
       if(task1.getAllocatedTime() < task2.getAllocatedTime())
           return -1;
    else if(task1.getAllocatedTime() > task2.getAllocatedTime())
           return 1;
    else
           return 0;
    }
}