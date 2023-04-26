package com.example.projectmanagementtool.Model;

import java.util.List;

public interface Component {

    public String getName();
    public String getDescription();
    public String owner();
    public double allocatedTime();
    public double deadline();

}
