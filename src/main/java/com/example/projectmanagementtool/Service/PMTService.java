package com.example.projectmanagementtool.Service;

import com.example.projectmanagementtool.Model.Project;
import com.example.projectmanagementtool.Model.Subproject;
import com.example.projectmanagementtool.Model.Task;
import com.example.projectmanagementtool.Model.User;
import com.example.projectmanagementtool.Repository.PMTRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PMTService {
    private PMTRepository pmtRepository;

    public PMTService(ApplicationContext context, @Value("${pmt.repository}") String impl) {
        pmtRepository = (PMTRepository) context.getBean(impl);
    }

    public void addTaskToDB(Task task) throws pmtException{
        pmtRepository.addTaskToDB(task);
    }

    public List<Task> getTasksFromSubproject(int subprojectID) {
        return pmtRepository.getTasksFromSubproject(subprojectID);
    }

    public List<Subproject> getSubProjects (int projectID) {
        return pmtRepository.getSubProjects(projectID);
    }

    public List<User> getAllUsers() {
        return pmtRepository.getAllUsers();
    }

    public User getUserFromID(int ID) {
        return pmtRepository.getUserFromID(ID);
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

    public List<Project> getAllProjects() {
        return pmtRepository.getAllProjects();
    }

    public void createUser(User user) {
        pmtRepository.createUser(user);
    }

    public void createProject(Project project) {
        pmtRepository.createProject(project);
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
}
