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
    UserRepository userRepository = new UserRepository();

    // Create a method that fetches all projects from the database

    // Create a method that fetches all users from the database

    // Create a method that fetches all tasks from the database

    // Create a method that fetches all subtasks from the database

    // simply gets the task-table from db

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList();
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM Tasks order by name";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("Name");
                String description = rs.getString("Description");
                int allocatedTime = rs.getInt("AllocatedTime");
                User owner = userRepository.getUserFromID(rs.getInt("OwnerID"));
                String Deadline = rs.getString("Deadline");
                int SubprojectID = rs.getInt("SubprojectID");
                String status = rs.getString("Status");
                taskList.add(new Task(id, name, description, allocatedTime, owner, Deadline, SubprojectID, status));
            }
            return taskList;
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    // Fetches all subprojects from the database for a specific project
    public List<Subproject> getSubProjects(int projectSearchID, String criteria) {
        List<Subproject> subprojectList = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            // "users.name as owner" lader os sortere efter projekt owners navn
            String SQL = "SELECT subprojects.*, users.name as owner FROM pmt_db.subprojects join users on users.id = subprojects.OwnerID  where ProjectID = ? order by " + criteria;
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setInt(1, projectSearchID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Subproject subproject;
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                String description = rs.getString("Description");
                User owner = userRepository.getUserFromID(rs.getInt("OwnerID"));
                String deadline = rs.getString("Deadline");
                int projectID = rs.getInt("ProjectID");
                subproject = new Subproject(id, name, description, owner, deadline, projectID);
                calculateTimeSpentAndAllocatedTimeForSubProjects(subproject);
                subprojectList.add(subproject);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
        return subprojectList;
    }


    // Adds new task to DB
    public void addTaskToDB(Task task, int ownerID) throws pmtException {
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "INSERT INTO Tasks (Name, Description, AllocatedTime, OwnerID, Deadline, SubprojectID, Status)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, task.getName());
            ps.setString(2, task.getDescription());
            ps.setDouble(3, task.getAllocatedTime());
            ps.setInt(4, ownerID);
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
                User owner = userRepository.getUserFromID(rs.getInt("OwnerID"));
                String deadline = rs.getString("Deadline");
                int projectID = rs.getInt("ProjectID");
                subproject = new Subproject(id, name, description, owner, deadline, projectID);
                calculateTimeSpentAndAllocatedTimeForSubProjects(subproject);
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
                int allocatedTime = rs.getInt("AllocatedTime");
                User owner = userRepository.getUserFromID(rs.getInt("OwnerID"));
                String deadline = rs.getString("Deadline");
                int subprojectID = rs.getInt("SubprojectID");
                String status = rs.getString("Status");

                // iterating tasks and adding it to the list if it has the correct subproject ID
                if (findSubprojectID == subprojectID) {
                    taskList.add(new Task(id, name, description, allocatedTime, owner, deadline, subprojectID, status));
                }

            }
            return taskList;
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }


    public void createProject(Project project, int ownerID){

        project.setSubprojectList(new ArrayList<>());
        try{
            Connection conn = ConnectionManager.getConnection();
            String SQL = "Insert into projects(name,Description,AllocatedTime,OwnerID,Deadline) values (?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, project.getName());
            ps.setString(2, project.getDescription());
            ps.setDouble(3, 0);
            ps.setInt(4, ownerID);
            ps.setString(5, project.getDeadline().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            ps.executeUpdate();
        }
        catch(SQLException e){
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public void createSubProject(Subproject subproject, int ownerID){
        try{
            Connection conn = ConnectionManager.getConnection();
            String SQL = "Insert into subprojects(name,Description,AllocatedTime,OwnerID,Deadline,ProjectID) values (?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, subproject.getName());
            ps.setString(2, subproject.getDescription());
            ps.setInt(3, 0);
            ps.setInt(4, ownerID);
            ps.setString(5, subproject.getDeadline().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))); //TODO double check this method
            ps.setInt(6, subproject.getProjectID());
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
                User owner = userRepository.getUserFromID(rs.getInt("OwnerID"));
                String Deadline = rs.getString("Deadline");
                return new Project(id, name, description, allocatedTime,owner, Deadline);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
        return null;
    }

    //Metoden beregner hvor meget tid der totalt er sat til et projekt og hvor meget af den tid der er blevet brugt på projektet.
    //Beregenes ved at summere den satte tid for tasks under et projekt sammen. Hvis et projekt er done bliver det summeret i timeSpent.
    public void calculateTimeSpentAndAllocatedTimeForProjects(Project project) {
        try {
            Connection conn = ConnectionManager.getConnection();
            //Regner hvor meget tid der er blevet brugt af projektet
            String SQL = "select sum(tasks.allocatedTIme) as timeSpent from tasks " +
                    "join subprojects where tasks.SubprojectID = subprojects.id " +
                    "and subprojects.projectid = ? and tasks.status = 'done';";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setInt(1, project.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                project.setTimeSpent(rs.getInt("timeSpent"));
            }

            //Regner den totalle allocated time ud.
            String SQL2 = "select sum(tasks.allocatedTime) as totalAllocatedTime " +
                    "from tasks join subprojects where tasks.SubprojectID = subprojects.id " +
                    "and subprojects.projectid = ?;";
             ps = conn.prepareStatement(SQL2);
            ps.setInt(1, project.getId());
             rs = ps.executeQuery();
             while(rs.next()) {
                 project.setAllocatedTime(rs.getInt("totalAllocatedTime"));
             }
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

public void calculateTimeSpentAndAllocatedTimeForSubProjects(Subproject subproject) {
        try {
            Connection conn = ConnectionManager.getConnection();
            //Regner hvor meget tid der er blevet brugt af projektet
            String SQL = "select sum(tasks.allocatedTIme) as timeSpent from tasks " +
                    "join subprojects where tasks.SubprojectID = subprojects.id " +
                    "and subprojects.id = ? and tasks.status = 'done';";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setInt(1, subproject.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                subproject.setTimeSpent(rs.getInt("timeSpent"));
            }

            //Regner den totalle allocated time ud.
            String SQL2 = "select sum(tasks.allocatedTime) as totalAllocatedTime " +
                    "from tasks join subprojects where tasks.SubprojectID = subprojects.id " +
                    "and subprojects.id = ?;";
             ps = conn.prepareStatement(SQL2);
            ps.setInt(1, subproject.getId());
             rs = ps.executeQuery();
             while(rs.next()) {
                 subproject.setAllocatedTime(rs.getInt("totalAllocatedTime"));
             }
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public List<Project> getAllProjectsByCriteria(String criteria) {
        List<Project> projectList = new ArrayList();
        try {
            Connection conn = ConnectionManager.getConnection();
            // "users.name as owner" lader os sortere efter projekt owners navn
            String SQL = "SELECT projects.*, users.name as owner FROM pmt_db.projects join users on users.id = projects.OwnerID order by " + criteria;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                String description = rs.getString("Description");
                int allocatedTime = rs.getInt("AllocatedTime");
                User owner = userRepository.getUserFromID(rs.getInt("OwnerID"));
                String Deadline = rs.getString("Deadline");
                Project project = new Project(id, name, description, allocatedTime, owner, Deadline);
                calculateTimeSpentAndAllocatedTimeForProjects(project);
                projectList.add(project);
            }
            return projectList;
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }



    //TODO check denne her
    public List<Subproject> getAllSubprojects() {
        List<Subproject> subprojectList = new ArrayList();
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM pmt_db.subprojects";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                Subproject subproject;
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                String description = rs.getString("Description");
                User owner = userRepository.getUserFromID(rs.getInt("OwnerID"));
                String deadline = rs.getString("Deadline");
                int projectID = rs.getInt("ProjectID");
                subproject = new Subproject(id, name, description, owner, deadline, projectID);
                calculateTimeSpentAndAllocatedTimeForSubProjects(subproject);
                subprojectList.add(subproject);
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

    public void moveTaskToArchived(int taskId) {
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "UPDATE tasks SET status = 'Archived' WHERE tasks.id = ?";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setInt(1, taskId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Task getTaskFromID(int taskId){

        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM tasks WHERE ID = ?";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setInt(1, taskId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                String description = rs.getString("Description");
                int allocatedTime = rs.getInt("AllocatedTime");
                User owner = userRepository.getUserFromID(rs.getInt("OwnerID"));
                String deadline = rs.getString("Deadline");
                int subprojectID = rs.getInt("subprojectID");
                String status = rs.getString("Status");
                return new Task( id,  name,  description,  allocatedTime,  owner,  deadline,  subprojectID,  status);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
        return null;
    }

    public void editTask(int taskID){
        try{
            Task task = getTaskFromID(taskID);

            Connection conn = ConnectionManager.getConnection();
            String SQL2 = "update tasks set name = ?, Description = ?, AllocatedTime = ?, OwnerID = ?, deadline = ? where ID = ?";
            PreparedStatement ps = conn.prepareStatement(SQL2);
            ps.setString(1, task.getName());
            ps.setString(2, task.getDescription());
            ps.setInt(3, task.getAllocatedTime());
            ps.setInt(4, task.getOwner().getId());
            ps.setString(5, task.getDeadline().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            ps.setInt(6, task.getId());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
