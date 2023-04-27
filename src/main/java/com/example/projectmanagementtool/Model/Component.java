package com.example.projectmanagementtool.Model;

import java.time.LocalDate;

public interface Component {

    public String getName();
    public String getDescription();
    public User getOwner();
    public double getAllocatedTime();
    public LocalDate getDeadline();

}
