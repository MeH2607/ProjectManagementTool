package com.example.projectmanagementtool.Repository;

import com.example.projectmanagementtool.Model.Project;
import com.example.projectmanagementtool.Model.Subproject;
import com.example.projectmanagementtool.Model.Task;
import com.example.projectmanagementtool.Model.User;
import com.example.projectmanagementtool.Repository.Util.ConnectionManager;
import com.example.projectmanagementtool.Service.pmtException;
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
                String status = rs.getString("Status");
                taskList.add(new Task(name, description, allocatedTime, Deadline, SubprojectID, status));
            }
            return taskList;
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    // Create a method that fetches all subprojects from the database for a specific project

    public List<Subproject> getSubProjects(int projectSearchID) {
        List<Subproject> subprojectList = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM pmt_db.subprojects WHERE ProjectID = ?";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setInt(1, projectSearchID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                String description = rs.getString("Description");
                double allocatedTime = rs.getDouble("AllocatedTime");
                int OwnerID = rs.getInt("OwnerID");
                String Deadline = rs.getString("Deadline");
                int ProjectID = rs.getInt("ProjectID");
                subprojectList.add(new Subproject(id, name, description, allocatedTime, OwnerID, Deadline, ProjectID));

            }
            System.out.println(subprojectList);

        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
        return subprojectList;
    }

    // Adds new task to DB
    public void addTaskToDB(Task task) throws pmtException {
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "INSERT INTO Tasks (Name, Description, AllocatedTime, OwnerID, Deadline, SubprojectID, Status)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, task.getName());
            ps.setString(2, task.getDescription());
            ps.setDouble(3, task.getAllocatedTime());
            ps.setInt(4, task.getOwner().getId());
            ps.setString(5, task.getDeadlineAsString());
            ps.setInt(6, task.getSubprojectID());
            ps.setString(7, "TODO");
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new pmtException(e.getMessage());
        }
    }


    // Retrieves a specific subproject from DB, from a subprojectID
    public Subproject getSubproject(int subprojectID) {
        System.out.println(subprojectID);
        Subproject subproject = new Subproject();
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM subprojects WHERE ID = ?";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setInt(1, subprojectID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                String description = rs.getString("Description");
                double allocatedTime = rs.getDouble("AllocatedTime");
                int OwnerID = rs.getInt("OwnerID");
                String Deadline = rs.getString("Deadline");
                int ProjectID = rs.getInt("ProjectID");
                subproject = new Subproject(id, name, description, allocatedTime, OwnerID, Deadline, ProjectID);

            }
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
        return subproject;
    }


    public List<Task> getTasksFromSubproject(int findSubprojectID) {

        List<Task> taskList = new ArrayList();
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM Tasks";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                // iterating all tasks
                int id = rs.getInt("id");
                String name = rs.getString("Name");
                String description = rs.getString("Description");
                double allocatedTime = rs.getDouble("AllocatedTime");
                int ownerID = rs.getInt("OwnerID");
                String deadline = rs.getString("Deadline");
                int subprojectID = rs.getInt("SubprojectID");
                String status = rs.getString("Status");

                // iterating tasks and adding it to the list if its the correct subproject ID
                if (findSubprojectID == subprojectID){
                    taskList.add(new Task(id, name, description, allocatedTime, deadline, subprojectID, status));
                }

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
            ps.setInt(4, project.getOwner().getId());
            ps.setString(5, project.getDeadline().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))); //TODO double check this method
            ps.executeUpdate();
        }
        catch(SQLException e){
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public Project getProjectFromID(int projectID) {
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM pmt_db.projects WHERE ID = ?";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setInt(1, projectID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                String description = rs.getString("Description");
                int allocatedTime = rs.getInt("AllocatedTime");
                int OwnerID = rs.getInt("OwnerID");
                String Deadline = rs.getString("Deadline");
                return new Project(id, name, description, OwnerID, allocatedTime, Deadline);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
        return null;
    }

    public void setRemainingTime(Project project){ //TODO implementer denne her færdig
        try{
            Connection conn = ConnectionManager.getConnection();
            String SQL = "SELECT ALLOCATEDTIME FROM SUBPROJECTS WHERE PROJECTID = ?";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setInt(1, project.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                project.setRemainingTime(project.getRemainingTime() + rs.getInt("AllocatedTime"));
            }
        }catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public List<Project> getAllProjects() {
        List<Project> projectList = new ArrayList();
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM pmt_db.projects";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                String description = rs.getString("Description");
                int allocatedTime = rs.getInt("AllocatedTime");
                int OwnerID = rs.getInt("OwnerID");
                String Deadline = rs.getString("Deadline");
                projectList.add(new Project(id, name, description, allocatedTime, OwnerID, Deadline));
            }
            return projectList;
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }



    public List<Project> getAllProjectsByCriteria(String criteria){
        List<Project> projectList = new ArrayList();
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM pmt_db.projects order by " + criteria;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                String description = rs.getString("Description");
                int allocatedTime = rs.getInt("AllocatedTime");
                int OwnerID = rs.getInt("OwnerID");
                String Deadline = rs.getString("Deadline");
                Project project = new Project(id, name, description, allocatedTime, OwnerID, Deadline);
                setRemainingTime(project);
                projectList.add(project);
            }
            return projectList;
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public List<Subproject> getAllSubprojects() {
        List<Subproject> subprojectList = new ArrayList();
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM pmt_db.subprojects";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                String description = rs.getString("Description");
                int allocatedTime = rs.getInt("AllocatedTime");
                int OwnerID = rs.getInt("OwnerID");
                String Deadline = rs.getString("Deadline");
                int ProjectID = rs.getInt("ProjectID");
                subprojectList.add(new Subproject(id, name, description, allocatedTime, OwnerID, Deadline, ProjectID));
            }
            return subprojectList;
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public void moveTaskToDoing(int taskId) {
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "UPDATE tasks SET status = 'Doing' WHERE tasks.id = ?";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setInt(1, taskId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void moveTaskToTodo(int taskId) {
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "UPDATE tasks SET status = 'TODO' WHERE tasks.id = ?";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setInt(1, taskId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void moveTaskToDone(int taskId) {
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "UPDATE tasks SET status = 'Done' WHERE tasks.id = ?";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setInt(1, taskId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
