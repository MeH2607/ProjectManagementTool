package com.example.projectmanagementtool.Controller;

import com.example.projectmanagementtool.Model.*;
import com.example.projectmanagementtool.Repository.UserRepository;
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
    private boolean isLoogedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    @GetMapping("")
    public String index(Model model, HttpSession session) {
        List<Project> projects = pmtService.getAllProjects();
        model.addAttribute("projects", projects);

        model.addAttribute("user",new User());

        List<String>roles = new ArrayList<>(List.of("Projektleder", "Programmør", "Webudvikler", "Backend udvikler", "Frontend udvikler"));
        model.addAttribute("roles", roles);



        return isLoogedIn(session) ? "Homepage" : "login";
    }

    @GetMapping("login")
    public String showLogin() {
        // return login form
        return "login";
    }

    @PostMapping("login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        // find user in repo - return admin1 if success
        User user = pmtService.getUser(email, password);
        if (user != null)
            if (user.getPassword().equals(password) && user.getEmail().equals(email)) {
                // create session for user and set session timeout to 30 sec (container default: 15 min)
                session.setAttribute("user", user);
                session.setMaxInactiveInterval(30);
                return  "redirect:/";

            }
        // wrong credentials
        model.addAttribute("wrongCredentials", true);
        return "login";
    }

    @GetMapping("project/{projectID}")
    public String subProjectOverview(@PathVariable int projectID,  Model model, HttpSession session) {

        List<Task> allTasks = pmtService.getAllTasks();
        List<Subproject> subprojects = pmtService.getSubProjects(projectID);
        List<Project> projects = pmtService.getAllProjects();
        Project project = pmtService.getProjectFromID(projectID);
        Subproject subproject = new Subproject();

        model.addAttribute("tasks", allTasks);
        model.addAttribute("subprojects", subprojects);
        model.addAttribute("projects", projects);
        model.addAttribute("project", project);

        // Sort allTasks into list for its status
        List<Task> todo = new ArrayList<Task>();
        List<Task> doing = new ArrayList<Task>();
        List<Task> done = new ArrayList<Task>();

        for (Task task : allTasks) {
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

        model.addAttribute("project", project);

        List<Task> list = pmtService.getAllTasks();
        model.addAttribute("list", list);

        return isLoogedIn(session) ? "project" : "login";
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
        List<User> allUsers = pmtService.getAllUsers();
        model.addAttribute("all_users", allUsers);
        return isLoogedIn(session) ? "allprojects" : "login";
    }
    @PostMapping("allprojects")
    public String addProjectToDB(@ModelAttribute("project") Project project, @RequestParam("ownerID") int ownerID) throws pmtException {
        System.out.println("Owner ID for new project is " + ownerID);
        pmtService.createProject(project, ownerID);
        System.out.println("Project " + project.getName() + "with owner " + pmtService.getUserFromID(ownerID).getName() + " has been created");
        return "redirect:/allprojects";
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
        pmtService.createProject(project, ownerID);
        System.out.println(project.getName() + " has been created");
        return "redirect:/";
    }

    @PostMapping("createSubproject")
    public String createSubprojectSuccess(@ModelAttribute("subproject") Subproject subproject, @RequestParam("ownerID") int ownerID){

        pmtService.createSubproject(subproject, ownerID);
        System.out.println(subproject.getName() + " has been created");
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


        return isLoogedIn(session) ? "subproject" : "login";
    }

    @PostMapping("project/{projectID}/create_task")
    public String addTaskToDB(@ModelAttribute("task") Task task, @RequestParam("ownerID") int ownerID) throws pmtException {

        System.out.println("Owner ID for new task is " + ownerID);

        pmtService.addTaskToDB(task, ownerID);

        System.out.println("Task " + task.getName() + "with assignee " + pmtService.getUserFromID(ownerID).getName() + " has been created");


        return "redirect:/project/{projectID}";
    }


    @PostMapping("project/{projectID}/moveTaskToDoing")
    public String moveTaskToDoing(@RequestParam("taskId") int taskId) {
        // the taskID in @RequestParam("taskId") is used to map the value of taskId parameter from the HTML

        pmtService.moveTaskToDoing(taskId);

        // Redirect back to the task list page
        return "redirect:/project/{projectID}";
    }

    @PostMapping("project/{projectID}/moveTaskToTodo")
    public String moveTaskToTodo(@RequestParam("taskId") int taskId) {
        // the taskID in @RequestParam("taskId") is used to map the value of taskId parameter from the HTML

        pmtService.moveTaskToTodo(taskId);

        // Redirect back to the task list page
        return "redirect:/project/{projectID}";
    }

    @PostMapping("project/{projectID}/moveTaskToDone")
    public String moveTaskToDone(@RequestParam("taskId") int taskId) {
        // the taskID in @RequestParam("taskId") is used to map the value of taskId parameter from the HTML

        pmtService.moveTaskToDone(taskId);

        // Redirect back to the task list page
        return "redirect:/project/{projectID}"; // TODO ikke sikker på om virker endnu
    }

}


