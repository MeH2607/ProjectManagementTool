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

        model.addAttribute("tasks", tasks);
        model.addAttribute("subprojects", subprojects);

        List<Task> list = pmtService.getTasksFromSubproject(projectID);


        return "subprojects";
    }

    @GetMapping("{subprojectID}")
    public String getSubproject(@PathVariable int subprojectID, Model model, HttpSession session) {

        // Here we need to retrieve all tasks from a specific Subprojects from the DB

        List<Task> subprojectTasks = pmtService.getTasksFromSubproject(subprojectID);

        model.addAttribute("tasks", subprojectTasks);


        return "taskoverview";
    }
}


