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

    @GetMapping("{subProjectID}")
    public String index(@PathVariable int subProjectID, Model model, HttpSession session) {
        Task task = new Task();
        model.addAttribute("task", task);
        List<Task> list = pmtService.getTasksFromSubproject(subProjectID);
        model.addAttribute("list", list);

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
}


