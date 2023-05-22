package com.example.projectmanagementtool.Model;

import java.time.LocalDate;

public interface Component {

    String getName();

    String getDescription();

    User getOwner();

    int getAllocatedTime(); // Total allocated time per task/subproject/project (not 'time remaining')

    LocalDate getDeadline();

}
