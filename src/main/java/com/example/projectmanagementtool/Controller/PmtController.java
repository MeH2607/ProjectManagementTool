package com.example.projectmanagementtool.Controller;
import com.example.projectmanagementtool.Model.Project;
import com.example.projectmanagementtool.Model.Subproject;
import com.example.projectmanagementtool.Model.Task;
import com.example.projectmanagementtool.Model.User;
import com.example.projectmanagementtool.Repository.ProjectsComparators.ProjectNameComparator;
import com.example.projectmanagementtool.Service.PMTService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
List<Task> task = pmtService.getAllTasks();

        model.addAttribute("task", task);
        List<Task> list = pmtService.getAllTasks();
        model.addAttribute("list", list);

        return "homepage";
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

        String criteria = new String();
        List<String>sortCriterias = new ArrayList<>(List.of("Name", "Owner", "Deadline"));
        model.addAttribute("sortCriterias", sortCriterias);
        model.addAttribute("criteria", criteria);

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
        List<Task> allTasksForSubproject = pmtService.getTasksFromSubproject(subprojectID);

        // Sort tasks into list for its status
        List<Task> todo = new ArrayList<Task>();
        List<Task> doing = new ArrayList<Task>();
        List<Task> done = new ArrayList<Task>();

        for (Task task : allTasksForSubproject) {
            switch (task.getStatus().toLowerCase()) {
                case "todo": todo.add(task); break;
                case "doing": doing.add(task); break;
                case "done": done.add(task); break;
            }
        }

        model.addAttribute("todo", todo);
        model.addAttribute("doing", doing);
        model.addAttribute("done", done);
        model.addAttribute("subproject", subproject);
        model.addAttribute("project", project);
        List<Task> list = pmtService.getAllTasks();
        model.addAttribute("list", list);


        return "subproject";
    }

    @GetMapping("createUser")
    public String createUser(Model model){

        model.addAttribute("user",new User());

        List<String>roles = new ArrayList<>(List.of("Projektleder", "Programmør", "Webudvikler", "Backend udvikler", "Frontend udvikler"));
        model.addAttribute("roles", roles);

        return "createUserForm";
    }

    @PostMapping("createUser")
    public String createUserSuccess(@ModelAttribute("user") User user){
        pmtService.createUser(user);
        System.out.println(user.getName() + " has been created");
        return "redirect:/";
    }

    @GetMapping("createProject")
    public String createProject(Model model){

        Project project = new Project();

        project.setOwner(new User());

        model.addAttribute("project", project);

        List<User> userList = pmtService.getAllUsers();

        model.addAttribute("userList", userList);


        return "createProjectForm";
    }

    @PostMapping("createProject")
    public String createProjectSuccess(@ModelAttribute("project") Project project){

        project.setOwner(pmtService.getUserFromID(project.getOwnerID()));
        pmtService.createProject(project);
        System.out.println(project.getName() + " has been created");

        return "redirect:/";
    }



    @GetMapping("allprojects/sortByName") //TODO find ud af hvad den bedste måde at lave sorting på er. Så man ikke skal lave seperete Controller metoder for hver sortering
    public String sortAllProjectsByName(Model model, HttpSession session) {
        List<Project> projects = pmtService.getAllProjectsByName();


        model.addAttribute("projects", projects);

        return "allProjects";
    }



    @PostMapping("allprojects/sorted")
    public String sortAllProjectsByCriteria(@ModelAttribute("criteria") String criteria, @ModelAttribute() HttpSession session) {


    return "redirect:/allprojects";
    }
    }


