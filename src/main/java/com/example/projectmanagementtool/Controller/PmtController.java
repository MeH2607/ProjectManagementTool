package com.example.projectmanagementtool.Controller;

import com.example.projectmanagementtool.Model.Project;
import com.example.projectmanagementtool.Model.Subproject;
import com.example.projectmanagementtool.Model.Task;
import com.example.projectmanagementtool.Model.User;
import com.example.projectmanagementtool.Service.PMTService;
import com.example.projectmanagementtool.Service.pmtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class PmtController {
    private final PMTService pmtService;

    public PmtController(PMTService pmtService) {
        this.pmtService = pmtService;
    }

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    //Shows the homepage with the navbar. No login required.
    @GetMapping("")
    public String index(Model model, HttpSession session) {
        List<Project> projects = pmtService.getAllProjectsByCriteria("name");
        List<Subproject> subprojects = pmtService.getAllSubProjectsByCriteria("name");
        List<String> roles = new ArrayList<>(List.of("Projektleder", "Programmør", "Webudvikler", "Backend udvikler", "Frontend udvikler"));

        model.addAttribute("projects", projects);
        model.addAttribute("subprojects", subprojects);
        model.addAttribute("user", new User());
        model.addAttribute("roles", roles);
        return "homepage";
    }

    //Shows the login page
    @GetMapping("login")
    public String showLogin(HttpServletRequest request, Model model) {
        String returnUrl = request.getRequestURI();
        model.addAttribute("returnUrl", returnUrl);
        return "login";
    }

    //Logs in the user if the credentials are correct
    @PostMapping("login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("returnUrl") String returnUrl, HttpSession session, Model model) {
        User user = pmtService.getUser(email, password);
        if (user != null) if (user.getPassword().equals(password) && user.getEmail().equals(email)) {
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(300);
            return "redirect:" + returnUrl;
        }
        model.addAttribute("wrongCredentials", true);
        return "login";
    }

    //Logs out the user
    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    //Shows a view with all projects, and the option to create a new project. You can also sort the projects by name, owner or deadline.
    @GetMapping("allprojects")
    public String allProjects(@RequestParam(required = false) String criteria, Model model, HttpSession session) {
        List<Project> projects = (criteria != null) ? pmtService.getAllProjectsByCriteria(criteria) : pmtService.getAllProjectsByCriteria("name");
        List<Subproject> subprojects = pmtService.getAllSubProjectsByCriteria("name");
        List<String> sortCriterias = new ArrayList<>(List.of("Name", "Owner", "Deadline"));
        List<User> allUsers = pmtService.getAllUsers();

        model.addAttribute("projects", projects);
        model.addAttribute("subprojects", subprojects);
        model.addAttribute("sortCriterias", sortCriterias);
        model.addAttribute("criteria", criteria);
        model.addAttribute("all_users", allUsers);
        return isLoggedIn(session) ? "allprojects" : "login";
    }

    //Shows a view with all subprojects belonging to specific project
    //You can create a new subproject. You can also sort the subprojects by name, owner or deadline.
    @GetMapping("project/{projectID}")
    public String subProjectOverview(@PathVariable int projectID, @RequestParam(required = false) String criteria, Model model, HttpSession session, HttpServletRequest request) {
        List<Subproject> subprojectsByProject = (criteria != null) ? pmtService.getSubProjects(projectID, criteria) : pmtService.getSubProjects(projectID, "name");
        List<Project> allProjectsByCriteria = pmtService.getAllProjectsByCriteria("name");
        List<User> allUsers = pmtService.getAllUsers();
        Project projectFromID = pmtService.getProjectFromID(projectID);
        String returnUrl = request.getRequestURI();
        List<String> sortCriterias = new ArrayList<>(List.of("Name", "Owner", "Deadline"));

        model.addAttribute("sortCriterias", sortCriterias);
        model.addAttribute("criteria", criteria);
        model.addAttribute("subprojects", subprojectsByProject);
        model.addAttribute("projects", allProjectsByCriteria);
        model.addAttribute("project", projectFromID);
        model.addAttribute("returnUrl", returnUrl);
        model.addAttribute("all_users", allUsers);
        return isLoggedIn(session) ? "project" : "login";
    }

    //Shows the profile page for the logged in user. You also get a view of all tasks assigned to the user.
    //You can also move the tasks between the different columns, and to the subprojects of your tasks
    @GetMapping("/profile")
    public String profile(Model model, HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute("user");
        List<Task> allTasks = pmtService.getAllTasks();
        List<Task> todo = new ArrayList<>();
        List<Task> doing = new ArrayList<>();
        List<Task> done = new ArrayList<>();
        String returnUrl = request.getRequestURI();

        for (Task task : allTasks) {
            switch (task.getStatus().toLowerCase()) {
                case "todo" -> todo.add(task);
                case "doing" -> doing.add(task);
                case "done" -> done.add(task);
            }
        }

        model.addAttribute("todo", todo);
        model.addAttribute("doing", doing);
        model.addAttribute("done", done);
        model.addAttribute("user", user);
        model.addAttribute("projects", pmtService.getAllProjectsByCriteria("name"));
        model.addAttribute("subprojects", pmtService.getAllSubProjectsByCriteria("name"));
        model.addAttribute("tasks", pmtService.getAllTasks());
        model.addAttribute("returnUrl", returnUrl);
        return isLoggedIn(session) ? "profile" : "login";
    }

    //Shows a view with all tasks belonging to a specific subproject. You can also move the tasks between the different columns
    @GetMapping("subproject/{subprojectID}")
    public String getSubproject(@PathVariable int subprojectID, Model model, HttpSession session, HttpServletRequest request) {

        Subproject subproject = pmtService.getSubProject(subprojectID);
        Project project = pmtService.getProjectFromID(subproject.getProjectID());
        List<Subproject> subprojects = pmtService.getSubProjects(subproject.getProjectID(), "name");
        List<Task> allTasksForSubproject = pmtService.getTasksFromSubproject(subprojectID);
        List<Project> projects = pmtService.getAllProjectsByCriteria("name");
        List<User> allUsers = pmtService.getAllUsers();
        List<Task> allTasks = pmtService.getAllTasks();
        String returnUrl = request.getRequestURI();
        Task task = new Task();

        List<String> sortCriterias = new ArrayList<>(List.of("Name", "Owner", "Deadline")); //Tilføjer sorterings kriterier som en liste

        // Sort tasks into list for its status
        List<Task> todo = new ArrayList<>();
        List<Task> doing = new ArrayList<>();
        List<Task> done = new ArrayList<>();

        for (Task taskWithStatus : allTasksForSubproject) {
            switch (taskWithStatus.getStatus().toLowerCase()) {
                case "todo" -> todo.add(taskWithStatus);
                case "doing" -> doing.add(taskWithStatus);
                case "done" -> done.add(taskWithStatus);
            }
        }

        List<Task> deadLineList = new ArrayList<>();
        for (Task taskDeadline : allTasks) {
            //Checker om der er 10 dage mellem dags dato og deadline og at en task ikke er sat til done
            if (!taskDeadline.getStatus().equals("DONE") && ChronoUnit.DAYS.between(LocalDate.now(), taskDeadline.getDeadline()) < 10) {
                deadLineList.add(taskDeadline);
            }
            model.addAttribute("deadLineList", deadLineList);
        }

        model.addAttribute("todo", todo);
        model.addAttribute("doing", doing);
        model.addAttribute("done", done);
        model.addAttribute("sortCriterias", sortCriterias);
        model.addAttribute("task", task);
        model.addAttribute("all_users", allUsers);
        model.addAttribute("subproject", subproject);
        model.addAttribute("subprojects", subprojects);
        model.addAttribute("projects", projects);
        model.addAttribute("project", project);
        model.addAttribute("list", allTasks);
        model.addAttribute("returnUrl", returnUrl);
        return isLoggedIn(session) ? "subproject" : "login";
    }

    @PostMapping("createUser")
    public String createUserSuccess(@ModelAttribute("user") User user) {
        pmtService.createUser(user);
        return "redirect:/";
    }

    @PostMapping("allprojects")
    public String addProjectToDB(@ModelAttribute("project") Project project, @RequestParam("ownerID") int ownerID) throws pmtException {
        pmtService.createProject(project, ownerID);
        return "redirect:/allprojects";
    }

    @PostMapping("project/{projectID}/createSubproject")
    public String createSubprojectSuccess(@ModelAttribute("subproject") Subproject subproject, @RequestParam("ownerID") int ownerID) {
        pmtService.createSubproject(subproject, ownerID);
        return "redirect:/project/{projectID}";
    }

    @PostMapping("subproject/{subprojectID}/create_task")
    public String addTaskToDB(@ModelAttribute("task") Task task, @RequestParam("ownerID") int ownerID) throws pmtException {
        pmtService.addTaskToDB(task, ownerID);
        return "redirect:/subproject/{subprojectID}";
    }

    // Archive task
    @PostMapping("subproject/{subprojectID}/move_task_to_archived")
    public String moveTaskToArchived(@RequestParam("taskId") int taskId, @RequestParam("returnUrl") String returnUrl) {
        // the taskID in @RequestParam("taskId") is used to map the value of taskId parameter from the HTML
        pmtService.moveTaskToArchived(taskId);
        return "redirect:" + returnUrl;
    }

    @PostMapping("subproject/{subprojectID}/moveTaskToDoing")
    public String moveTaskToDoing(@RequestParam("taskId") int taskId, @RequestParam("returnUrl") String returnUrl) {
        // the taskID in @RequestParam("taskId") is used to map the value of taskId parameter from the HTML
        pmtService.moveTaskToDoing(taskId);
        return "redirect:" + returnUrl;
    }

    @PostMapping("subproject/{subprojectID}/moveTaskToTodo")
    public String moveTaskToTodo(@RequestParam("taskId") int taskId, @RequestParam("returnUrl") String returnUrl) {
        pmtService.moveTaskToTodo(taskId);
        return "redirect:" + returnUrl;
    }

    @PostMapping("subproject/{subprojectID}/moveTaskToDone")
    public String moveTaskToDone(@RequestParam("taskId") int taskId, @RequestParam("returnUrl") String returnUrl) {
        pmtService.moveTaskToDone(taskId);
        return "redirect:" + returnUrl;
    }
}


