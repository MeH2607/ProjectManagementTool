package com.example.projectmanagementtool.Repository;

import com.example.projectmanagementtool.Model.Task;
import com.example.projectmanagementtool.Repository.Util.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository("PMTRepository")
public class PMTRepository {

    // Create a method that fetches all projects from the database

    // Create a method that fetches all users from the database

    // Create a method that fetches all tasks from the database

    // Create a method that fetches all subtasks from the database

    // simply gets the task-table from db


    public List<Task> getTasksFromSubproject(int findSubprojectID) {

        List<Task> taskList = new ArrayList();
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM Tasks";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                // iterating all tasks
                String name = rs.getString("Name");
                String description = rs.getString("Description");
                double allocatedTime = rs.getDouble("AllocatedTime");
                int ownerID = rs.getInt("OwnerID");
                String deadline = rs.getString("Deadline");
                int subprojectID = rs.getInt("SubprojectID");

                // iterating tasks and adding it to the list if its the correct subproject ID
                if (findSubprojectID == subprojectID){
                    taskList.add(new Task(name, description, allocatedTime, ownerID, deadline, subprojectID));
                }

            }
            return taskList;
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
}
