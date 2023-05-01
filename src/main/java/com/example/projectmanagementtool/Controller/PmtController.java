package com.example.projectmanagementtool.Controller;
import com.example.projectmanagementtool.Model.Project;
import com.example.projectmanagementtool.Model.Task;
import com.example.projectmanagementtool.Model.User;
import com.example.projectmanagementtool.Service.PMTService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
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
        List<Task> list = pmtService.getAllTasks();
        model.addAttribute("list", list);

        return "index";
    }
    @GetMapping("subprojects")
    public String subProjectOverview(Model model, HttpSession session) {
        Task task = new Task();
        Project project = new Project();
        model.addAttribute("task", task);
        model.addAttribute("project", project);
        List<Task> list = pmtService.getAllTasks();
        model.addAttribute("list", list);

        return "subprojects";
    }

    @GetMapping("createUser")
    public String createUser(Model model){

        model.addAttribute("user",new User());

        List<String>roles = new ArrayList<>(List.of("Projektleder", "Programmør", "Webudvikler", "Backend udvikler", "Frontend udvikler"));
        model.addAttribute("roles", roles);

        return "createUserForm";
    }
}


