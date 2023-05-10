package com.example.projectmanagementtool.Controller;

import com.example.projectmanagementtool.Model.Project;
import com.example.projectmanagementtool.Model.Subproject;
import com.example.projectmanagementtool.Model.Task;
import com.example.projectmanagementtool.Model.User;
import com.example.projectmanagementtool.Service.PMTService;
import com.example.projectmanagementtool.Service.pmtException;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        List<Project> projects = pmtService.getAllProjects();
        model.addAttribute("projects", projects);

        return "index";
    }
    @GetMapping("project/{projectID}")
    public String subProjectOvervisew(@PathVariable int projectID,  Model model, HttpSession session) {

        List<Task> tasks = pmtService.getAllTasks();
        List<Subproject> subprojects = pmtService.getSubProjects(projectID);
        List<Project> projects = pmtService.getAllProjects();
        Project project = pmtService.getProjectFromID(projectID);


        model.addAttribute("tasks", tasks);
        model.addAttribute("subprojects", subprojects);
        model.addAttribute("projects", projects);
        model.addAttribute("project", project);

        return "project";
    }

    //Denne metode viser projekter som de kommer fra databasen uden sortering, men hvis man vælger en sorteringsmulighed, så bliver den sorteret
    @GetMapping("allprojects")
    public String allProjects(@RequestParam(required = false) String criteria,  Model model, HttpSession session) {
        List<Project> projects;
        if(criteria != null)
         projects = pmtService.getAllProjectsByCriteria(criteria);
        else
            projects = pmtService.getAllProjectsByCriteria("name");

        model.addAttribute("projects", projects);


        List<String> sortCriterias = new ArrayList<>(List.of("Name", "Owner", "Deadline"));
        model.addAttribute("sortCriterias", sortCriterias);
        model.addAttribute("criteria", criteria);

        return "allProjects";
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
    public String createProjectSuccess(@ModelAttribute("project") Project project, @RequestParam("ownerID") int ownerID){

        // project.setOwner(pmtService.getUserFromID(project.getOwnerID()));

        pmtService.createProject(project, ownerID);
        System.out.println(project.getName() + " has been created");

        return "redirect:/";
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

        // Predefinerer subproject ID ind i "create task"
        Task task = new Task();
        model.addAttribute("task", task);

        List<User> allUsers = pmtService.getAllUsers();
        model.addAttribute("all_users", allUsers);

        model.addAttribute("subproject", subproject);
        model.addAttribute("project", project);

        List<Task> list = pmtService.getAllTasks();
        model.addAttribute("list", list);


        return "subproject";
    }

    @PostMapping("subproject/{subprojectID}/create_task")
    public String addTaskToDB(@ModelAttribute("task") Task task, @PathVariable int subprojectID) throws pmtException {

        System.out.println("Task subproject ID after creating task: " + task.getSubprojectID());

        task.setOwner(pmtService.getUserFromID(task.getOwnerID()));

        pmtService.addTaskToDB(task);
        return "redirect:/subproject/{subprojectID}";
    }

    @PostMapping("subproject/{subprojectID}/moveTaskToDoing")
    public String moveTaskToDoing(@RequestParam("taskId") int taskId, @PathVariable int subprojectID) {
        // the taskID in @RequestParam("taskId") is used to map the value of taskId parameter from the HTML

        pmtService.moveTaskToDoing(taskId);

        // Redirect back to the task list page
        return "redirect:/subproject/{subprojectID}"; // TODO ikke sikker på om virker endnu
    }

    @PostMapping("subproject/{subprojectID}/moveTaskToTodo")
    public String moveTaskToTodo(@RequestParam("taskId") int taskId, @PathVariable int subprojectID) {
        // the taskID in @RequestParam("taskId") is used to map the value of taskId parameter from the HTML

        pmtService.moveTaskToTodo(taskId);


        // Redirect back to the task list page
        return "redirect:/subproject/{subprojectID}"; // TODO ikke sikker på om virker endnu
    }

    @PostMapping("subproject/{subprojectID}/moveTaskToDone")
    public String moveTaskToDone(@RequestParam("taskId") int taskId, @PathVariable int subprojectID) {
        // the taskID in @RequestParam("taskId") is used to map the value of taskId parameter from the HTML

        pmtService.moveTaskToDone(taskId);

        // Redirect back to the task list page
        return "redirect:/subproject/{subprojectID}"; // TODO ikke sikker på om virker endnu
    }

}


