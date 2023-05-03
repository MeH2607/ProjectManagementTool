package com.example.projectmanagementtool.Controller;
import com.example.projectmanagementtool.Model.Project;
import com.example.projectmanagementtool.Model.Subproject;
import com.example.projectmanagementtool.Model.Task;
import com.example.projectmanagementtool.Service.PMTService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class PmtController {
    private PMTService pmtService;

    public PmtController(PMTService pmtService) {
        this.pmtService = pmtService;
    }

    @GetMapping("")
    public String index(Model model, HttpSession session) {
        Task task = new Task();
        model.addAttribute("task", task);

        return "index";
    }
    @GetMapping("project/{projectID}")
    public String subProjectOvervisew(@PathVariable int projectID,  Model model, HttpSession session) {

        List<Task> tasks = pmtService.getAllTasks();
        List<Subproject> subprojects = pmtService.getSubProjects(projectID);
        Project project = pmtService.getProjectFromID(projectID);

        model.addAttribute("tasks", tasks);
        model.addAttribute("subprojects", subprojects);
        model.addAttribute("project", project);

        List<Task> list = pmtService.getTasksFromSubproject(projectID);

        return "project";
    }

    @GetMapping("allprojects")
    public String allProjects(Model model, HttpSession session) {
        List<Project> projects = pmtService.getAllProjects();
        model.addAttribute("projects", projects);

        return "allProjects";
    }


    @GetMapping("subproject/{subprojectID}")
    public String getSubproject(@PathVariable int subprojectID, Model model, HttpSession session) {
        // List<Task> subprojectTasks = pmtService.getTasksFromSubproject(subprojectID);

        // Retrieving the subproject itself
        Subproject subproject = pmtService.getSubProject(subprojectID);

        // Retrieving the project that the subproject belongs to
        Project project = pmtService.getProjectFromID(subproject.getProjectID());

        // Retrieving all tasks from the specific Subprojects from the DB
        List<Task> subprojectTasks = pmtService.getTasksFromSubproject(subprojectID);

        model.addAttribute("tasks", subprojectTasks);
        model.addAttribute("subproject", subproject);
        model.addAttribute("project", project);


        return "subproject";
    }
}


