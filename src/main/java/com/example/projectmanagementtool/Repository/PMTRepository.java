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

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM Tasks";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String name = rs.getString("Name");
                String description = rs.getString("Description");
                double allocatedTime = rs.getDouble("AllocatedTime");
                int OwnerID = rs.getInt("OwnerID");
                String Deadline = rs.getString("Deadline");
                int SubprojectID = rs.getInt("SubprojectID");
                taskList.add(new Task(name, description, allocatedTime, OwnerID, Deadline, SubprojectID));
            }
            return taskList;
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
