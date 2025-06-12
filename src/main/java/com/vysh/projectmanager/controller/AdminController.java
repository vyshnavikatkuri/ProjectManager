package com.vysh.projectmanager.controller;

import com.vysh.projectmanager.model.Project;
import com.vysh.projectmanager.model.Task;
import com.vysh.projectmanager.model.User;
import com.vysh.projectmanager.service.ProjectService;
import com.vysh.projectmanager.service.TaskService;
import com.vysh.projectmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProjectService projectService;
    private final TaskService taskService;
    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("projects", projectService.getAllProjects());
        return "admin-dashboard";
    }

    @GetMapping("/project/{projectId}")
    public String viewProjectTasks(@PathVariable Long projectId, Model model) {
        Project project = projectService.getProjectById(projectId);
        List<Task> tasks = taskService.getTasksByProjectId(projectId);
        model.addAttribute("project", project);
        model.addAttribute("tasks", tasks);
        return "project-tasks";
    }

    @GetMapping("/project/{projectId}/create-task-form")
    public String createTaskForm(@PathVariable Long projectId, Model model) {
        Project project = projectService.getProjectById(projectId);
        List<User> users = userService.getAllUsers();
        model.addAttribute("project", project);
        model.addAttribute("users", users);
        model.addAttribute("task", new Task());
        return "create-task-form";
    }

    @PostMapping("/project/{projectId}/create-task")
    public String createTask(@PathVariable Long projectId, @ModelAttribute Task task,
                             @RequestParam String username, Model model) {
        taskService.assignTaskToUserAndProject(task, username, projectId);
        return "redirect:/admin/project/" + projectId;
    }
}
