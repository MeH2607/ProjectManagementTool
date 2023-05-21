package com.example.projectmanagementtool.Controller;

import com.example.projectmanagementtool.Model.*;
import com.example.projectmanagementtool.Service.PMTService;
import com.example.projectmanagementtool.Service.pmtException;
import jakarta.servlet.http.HttpServletRequest;
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
    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    @GetMapping("")
    public String index(Model model, HttpSession session) {
        List<Project> projects = pmtService.getAllProjectsByCriteria("name");
        List<Subproject> subprojects = pmtService.getAllSubProjectsByCriteria("name");
        System.out.println(subprojects);
        model.addAttribute("projects", projects);
        model.addAttribute("subprojects", subprojects);

        model.addAttribute("user",new User());

        List<String>roles = new ArrayList<>(List.of("Projektleder", "Programmør", "Webudvikler", "Backend udvikler", "Frontend udvikler"));
        model.addAttribute("roles", roles);

        return "Homepage";
    }

    @GetMapping("login")
    public String showLogin(HttpServletRequest request, Model model) {

        String returnUrl = request.getRequestURI();
        model.addAttribute("returnUrl", returnUrl);
        System.out.println("print from login" + returnUrl);

        // return login form
        return "login";
    }


    @PostMapping("login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        @RequestParam("returnUrl") String returnUrl,
                        HttpSession session,
                        Model model) {
        // find user in repo - return admin1 if success
        User user = pmtService.getUser(email, password);



        if (user != null)
            if (user.getPassword().equals(password) && user.getEmail().equals(email)) {
                // create session for user and set session timeout to 30 sec (container default: 15 min)
                session.setAttribute("user", user);
                session.setMaxInactiveInterval(300);
                return "redirect:" + returnUrl;

            }
        // wrong credentials
        model.addAttribute("wrongCredentials", true);

        return "login";
    }
    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("project/{projectID}")
    public String subProjectOverview(@PathVariable int projectID, @RequestParam(required = false) String criteria, Model model, HttpSession session, HttpServletRequest request) {

        List<Subproject> subprojects = (criteria != null) ? pmtService.getSubProjects(projectID, criteria) : pmtService.getSubProjects(projectID, "name");
        List<Project> projects = pmtService.getAllProjectsByCriteria("name");
        Project project = pmtService.getProjectFromID(projectID);
        List<Task> allTasks = pmtService.getAllTasks();
        List<String> sortCriterias = new ArrayList<>(List.of("Name", "Owner", "Deadline")); //Tilføjer sorterings kriterier som en liste

        model.addAttribute("sortCriterias", sortCriterias);
        model.addAttribute("criteria", criteria);
        model.addAttribute("tasks", allTasks);
        model.addAttribute("subprojects", subprojects);
        model.addAttribute("projects", projects);
        model.addAttribute("project", project);

        String returnUrl = request.getRequestURI();
        model.addAttribute("returnUrl", returnUrl);

        // Sort allTasks into list for its status
        List<Task> todo = new ArrayList<Task>();
        List<Task> doing = new ArrayList<Task>();
        List<Task> done = new ArrayList<Task>();

        for (Task task : allTasks) {
            switch (task.getStatus().toLowerCase()) {
                case "todo":
                    todo.add(task);
                    break;
                case "doing":
                    doing.add(task);
                    break;
                case "done":
                    done.add(task);
                    break;
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

        return isLoggedIn(session) ? "project" : "login";
    }

    //Denne metode viser projekter som de kommer fra databasen uden sortering, men hvis man vælger en sorteringsmulighed, så bliver den sorteret
    @GetMapping("allprojects")
    public String allProjects(@RequestParam(required = false) String criteria, Model model, HttpSession session) {
        List<Project> projects = (criteria != null) ? pmtService.getAllProjectsByCriteria(criteria) : pmtService.getAllProjectsByCriteria("name");
        List<Subproject> subprojects = pmtService.getAllSubProjectsByCriteria("name");

        model.addAttribute("projects", projects);
        model.addAttribute("subprojects", subprojects);


        List<String> sortCriterias = new ArrayList<>(List.of("Name", "Owner", "Deadline"));
        model.addAttribute("sortCriterias", sortCriterias);
        model.addAttribute("criteria", criteria);
        List<User> allUsers = pmtService.getAllUsers();
        model.addAttribute("all_users", allUsers);
        return isLoggedIn(session) ? "allprojects" : "login";
    }

    @PostMapping("allprojects")
    public String addProjectToDB(@ModelAttribute("project") Project project, @RequestParam("ownerID") int ownerID) throws pmtException {
        System.out.println("Owner ID for new project is " + ownerID);
        pmtService.createProject(project, ownerID);
        System.out.println("Project " + project.getName() + "with owner " + pmtService.getUserFromID(ownerID).getName() + " has been created");
        return "redirect:/allprojects";
    }

    @PostMapping("createUser")
    public String createUserSuccess(@ModelAttribute("user") User user) {
        pmtService.createUser(user);
        System.out.println(user.getName() + " has been created");
        return "redirect:/";
    }

    @GetMapping("createProject")
    public String createProject(Model model) {

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
    public String getSubproject(@PathVariable int subprojectID, Model model, HttpSession session, HttpServletRequest request) {

        Subproject subproject = pmtService.getSubProject(subprojectID);
        Project project = pmtService.getProjectFromID(subproject.getProjectID());

        List<Subproject> subprojects = pmtService.getSubProjects(subproject.getProjectID(), "name");
        List<Task> allTasksForSubproject = pmtService.getTasksFromSubproject(subprojectID);
        List<Project> projects = pmtService.getAllProjectsByCriteria("name");
        List<String> sortCriterias = new ArrayList<>(List.of("Name", "Owner", "Deadline")); //Tilføjer sorterings kriterier som en liste


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

        model.addAttribute("sortCriterias", sortCriterias);

        // Predefinerer subproject ID ind i "create task"
        Task task = new Task();
        model.addAttribute("task", task);

        List<User> allUsers = pmtService.getAllUsers();
        model.addAttribute("all_users", allUsers);

        model.addAttribute("subproject", subproject);
        model.addAttribute("subprojects", subprojects);
        model.addAttribute("projects", projects);
        model.addAttribute("project", project);

        List<Task> list = pmtService.getAllTasks();
        model.addAttribute("list", list);


        String returnUrl = request.getRequestURI();
        model.addAttribute("returnUrl", returnUrl);
        System.out.println("print from subproject" + returnUrl);

        return isLoggedIn(session) ? "subproject" : "login";
    }

    @PostMapping("subproject/{subprojectID}/create_task")
    public String addTaskToDB(@ModelAttribute("task") Task task, @RequestParam("ownerID") int ownerID) throws pmtException {

        System.out.println("Owner ID for new task is " + ownerID);

        pmtService.addTaskToDB(task, ownerID);

        System.out.println("Task " + task.getName() + "with assignee " + pmtService.getUserFromID(ownerID).getName() + " has been created");


        return "redirect:/subproject/{subprojectID}";
    }

    // Archive task
    @PostMapping("subproject/{subprojectID}/move_task_to_archived")
    public String moveTaskToArchived(@RequestParam("taskId") int taskId,
                                     @RequestParam("returnUrl") String returnUrl) {
        // the taskID in @RequestParam("taskId") is used to map the value of taskId parameter from the HTML

        pmtService.moveTaskToArchived(taskId);

        System.out.println("print from movetask" + returnUrl);


        // Redirect back to the task list page
        return "redirect:" + returnUrl;
    }

    @PostMapping("subproject/{subprojectID}/moveTaskToDoing")
    public String moveTaskToDoing(@RequestParam("taskId") int taskId,
                                  @RequestParam("returnUrl") String returnUrl) {
        // the taskID in @RequestParam("taskId") is used to map the value of taskId parameter from the HTML

        pmtService.moveTaskToDoing(taskId);

        // Redirect back to the task list page
        return "redirect:" + returnUrl;
    }

    @PostMapping("subproject/{subprojectID}/moveTaskToTodo")
    public String moveTaskToTodo(@PathVariable("subprojectID") int subprojectID,
                                 @RequestParam("taskId") int taskId,
                                 @RequestParam("returnUrl") String returnUrl) {
        pmtService.moveTaskToTodo(taskId);

        // Redirect back to the original page
        return "redirect:" + returnUrl;
    }


    @PostMapping("subproject/{subprojectID}/moveTaskToDone")
    public String moveTaskToDone(@RequestParam("taskId") int taskId, @RequestParam("returnUrl") String returnUrl) {
        // the taskID in @RequestParam("taskId") is used to map the value of taskId parameter from the HTML

        pmtService.moveTaskToDone(taskId);

        // Redirect back to the task list page
        return "redirect:" + returnUrl;
    }
    @GetMapping("/profile")
    public String profile(Model model, HttpSession session, HttpServletRequest request) {

        User user = (User) session.getAttribute("user");
        List<Task> allTasks = pmtService.getAllTasks();
        model.addAttribute("user", user);
        model.addAttribute("projects", pmtService.getAllProjectsByCriteria("name"));
        model.addAttribute("subprojects", pmtService.getAllSubProjectsByCriteria("name"));
        model.addAttribute("tasks", pmtService.getAllTasks());

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


        String returnUrl = request.getRequestURI();
        model.addAttribute("returnUrl", returnUrl);
        System.out.println("print from prpfile" + returnUrl);



        return isLoggedIn(session) ? "profile" : "login";
    }



}


