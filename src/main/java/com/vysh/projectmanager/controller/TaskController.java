package com.vysh.projectmanager.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vysh.projectmanager.model.Task;
import com.vysh.projectmanager.model.User;
import com.vysh.projectmanager.repository.UserRepository;
import com.vysh.projectmanager.service.ProjectService;
import com.vysh.projectmanager.service.TaskService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;
    private final ProjectService projectService;

    // Admin View for All Tasks + Project Dropdown
    @GetMapping("/admin")
    public String adminTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("task", new Task());
        return "admin-tasks";
    }

    // Create New Task with Assigned User and Project
   @PostMapping("/admin/create")
    public String createTask(@ModelAttribute Task task,
                         @RequestParam String username,
                         @RequestParam Long projectId,
                         @AuthenticationPrincipal UserDetails currentUser) {

    User createdBy = userRepository.findByUsername(currentUser.getUsername()).orElse(null);

    if (createdBy != null) {
        task.setCreatedBy(createdBy); // Set the creator
        taskService.assignTaskToUserAndProject(task, username, projectId);
    }
    return "redirect:/tasks/admin";
}

    // // User View to See Own Tasks
    // @GetMapping("/user")
    // public String userTasks(@AuthenticationPrincipal UserDetails userDetails, Model model) {
    //     User user = userRepository.findByUsername(userDetails.getUsername()).orElse(null);
    //     if (user != null) {
    //         List<Task> tasks = taskService.getTasksForUser(user);
    //         model.addAttribute("tasks", tasks);
    //     }
    //     return "user-tasks";
    // }
   


    // Update Task Status (by user)
    @PostMapping("/user/updateStatus")
    public String updateStatus(@RequestParam Long taskId, @RequestParam Task.Status status) {
        Task task = taskService.getTaskById(taskId).orElse(null);
        if (task != null) {
            task.setStatus(status);
            taskService.updateTask(task);
        }
        return "redirect:/tasks/user";
    }
  
//     @PostMapping("/{taskId}/user-delete")
// public String softDeleteForUser(@PathVariable Long taskId) {
//     Task task = taskService.getTaskById(taskId);
//     if (task != null && task.getStatus() == Task.Status.COMPLETED) {
//         task.setVisibleToUser(false);
//         taskService.saveTask(task);
//     }
//     return "redirect:/tasks/user";
// }

// @PostMapping("/{taskId}/admin-delete")
// public String hardDeleteByAdmin(@PathVariable Long taskId) {
//     Task task = taskService.getTaskById(taskId);
//     if (task != null && task.getStatus() == Task.Status.COMPLETED) {
//         taskService.deleteTask(taskId);
//     }
//     return "redirect:/tasks/admin";
// }
    
   // Update User's View (show tasks with visibleToUser filter)
@GetMapping("/user")
public String userTasks(@AuthenticationPrincipal UserDetails userDetails, Model model) {
    User user = userRepository.findByUsername(userDetails.getUsername()).orElse(null);
    if (user != null) {
        // Fetch only tasks that are visible to user
        List<Task> tasks = taskService.getTasksForUser(user).stream()
                                      .filter(task -> task.getVisibleToUser() != null && task.getVisibleToUser())
                                      .collect(Collectors.toList());
        model.addAttribute("tasks", tasks);
    }
    return "user-tasks";
}

// Admin delete (hard delete for completed tasks)
@PostMapping("/admin/{taskId}/delete")
public String deleteTask(@PathVariable Long taskId) {
    Task task = taskService.getTaskById(taskId).orElse(null);
    if (task != null && task.getStatus() == Task.Status.COMPLETED) {
        taskService.deleteTask(taskId);
    }
    return "redirect:/tasks/admin";
}

// Soft delete for user (hide completed tasks)
@PostMapping("/user/{taskId}/hide")
public String hideCompletedTaskForUser(@PathVariable Long taskId) {
    Task task = taskService.getTaskById(taskId).orElse(null);
    if (task != null && task.getStatus() == Task.Status.COMPLETED) {
        task.setVisibleToUser(false);  // Hide the task for the user
        taskService.saveTask(task);  // Save changes
    }
    return "redirect:/tasks/user";
}


}
