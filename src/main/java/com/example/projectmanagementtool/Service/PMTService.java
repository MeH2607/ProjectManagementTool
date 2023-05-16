package com.example.projectmanagementtool.Service;

import com.example.projectmanagementtool.Model.Project;
import com.example.projectmanagementtool.Model.Subproject;
import com.example.projectmanagementtool.Model.Task;
import com.example.projectmanagementtool.Model.User;
import com.example.projectmanagementtool.Repository.PMTRepository;
import com.example.projectmanagementtool.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PMTService {
    private PMTRepository pmtRepository;
    private UserRepository userRepository;

    public PMTService(ApplicationContext context, @Value("${pmt.repository}") String impl, @Value("${user.repository}") String impl2) {
        pmtRepository = (PMTRepository) context.getBean(impl);
        userRepository = (UserRepository) context.getBean(impl2);
    }

    public void addTaskToDB(Task task, int ownerID) throws pmtException{
        pmtRepository.addTaskToDB(task, ownerID);
    }

    public List<Task> getTasksFromSubproject(int subprojectID) {
        return pmtRepository.getTasksFromSubproject(subprojectID);
    }

    public List<Subproject> getSubProjects (int projectID, String criteria) {
        return pmtRepository.getSubProjects(projectID, criteria);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public User getUserFromID(int ID) {
        return userRepository.getUserFromID(ID);
    }
    public User getUser(String email, String password) {
        return userRepository.getUser(email, password);
    }

    public List<Task> getAllTasks() {
        return pmtRepository.getAllTasks();
    }

    public Project getProjectFromID(int projectID) {
        return pmtRepository.getProjectFromID(projectID);
    }
    public Subproject getSubProject(int subprojectID) {
        return pmtRepository.getSubproject(subprojectID);
    }

    public void createUser(User user) {
        userRepository.createUser(user);
    }

    public void createProject(Project project, int ownerID) {
        pmtRepository.createProject(project, ownerID);
    }

    public void createSubproject(Subproject subproject, int ownerID) {
        pmtRepository.createSubProject(subproject, ownerID);
    }

    public List<Project>getAllProjectsByCriteria(String criteria) {
        return pmtRepository.getAllProjectsByCriteria(criteria);
    }

    public List<Subproject> getSubProjectsByCriteria(int projectID, String criteria) {
        return pmtRepository.getSubProjects(projectID, criteria);
    }

    public void moveTaskToDoing(int taskId) {
        pmtRepository.moveTaskToDoing(taskId);
    }

    public void moveTaskToTodo(int taskId) {
        pmtRepository.moveTaskToTodo(taskId);
    }

    public void moveTaskToDone(int taskId) {
        pmtRepository.moveTaskToDone(taskId);
    }

    public void moveTaskToArchived(int taskId) {
        pmtRepository.moveTaskToArchived(taskId);
    }
    public void editTask(int taskID){
        pmtRepository.editTask(taskID);
    }
}
