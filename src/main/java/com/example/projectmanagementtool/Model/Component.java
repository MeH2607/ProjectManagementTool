package com.example.projectmanagementtool.Model;

import java.time.LocalDate;
import java.util.List;

public interface Component {

    public String getName();
    public String getDescription();
    public User owner();
    public double allocatedTime();
    public LocalDate deadline();

}
