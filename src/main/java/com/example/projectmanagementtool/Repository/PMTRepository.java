package com.example.projectmanagementtool.Repository;

import com.example.projectmanagementtool.Model.Project;
import com.example.projectmanagementtool.Model.Task;
import com.example.projectmanagementtool.Model.User;
import com.example.projectmanagementtool.Repository.Util.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository("PMTRepository")
public class PMTRepository {

    // Create a method that fetches all projects from the database

    // Create a method that fetches all users from the database

    // Create a method that fetches all tasks from the database

    // Create a method that fetches all subtasks from the database

    // simply gets the task-table from db
    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList();
        try {
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
                taskList.add(new Task(name, description, allocatedTime, new User(420, "null", "null"), Deadline, SubprojectID));
            }
            return taskList;
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public User getUserFromID(int id) {
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM Users WHERE ID = ?";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("Name");
                String role = rs.getString("Role");
                return new User(id, name, role);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
        return null;
    }

    public List<User> getAllUsers(){
        try{
            Connection conn = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM Users";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            List<User> userList = new ArrayList();
            while (rs.next()){
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                String role = rs.getString("Role");
                userList.add(new User(id, name, role));
            }
            return userList;
        }
        catch (SQLException e){
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public void createUser(User user){
        try {
            Connection conn = ConnectionManager.getConnection();
        String SQL = "Insert into Users (Name, Role) values (?,?)";
            PreparedStatement ps = conn.prepareStatement(SQL);
          ps.setString(1, user.getName());
          ps.setString(2, user.getRole());
            ps.executeUpdate();
        }   catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }



    public void createProject(Project project){
        System.out.println("Repo " + project.getName() + " " + project.getOwner().getName());

        project.setSubprojectList(new ArrayList<>());
        try{
            Connection conn = ConnectionManager.getConnection();
            String SQL = "Insert into projects(name,Description,AllocatedTime,OwnerID,Deadline) values (?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, project.getName());
            ps.setString(2, project.getDescription());
            ps.setDouble(3, 0);
            ps.setInt(4, );
            ps.setString(5, project.getDeadline().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))); //TODO double check this method
            ps.executeUpdate();
        }
        catch(SQLException e){
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

}
