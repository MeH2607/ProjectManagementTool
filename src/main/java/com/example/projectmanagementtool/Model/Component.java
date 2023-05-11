package com.example.projectmanagementtool.Model;

import java.time.LocalDate;

public interface Component {

    public String getName();
    public String getDescription();
    public User getOwner();
    public int getAllocatedTime(); // Total allocated time per task/subproject/project (not 'time remaining')
    public LocalDate getDeadline();

}
