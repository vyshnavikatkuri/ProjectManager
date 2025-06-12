package com.vysh.projectmanager.controller;

import com.vysh.projectmanager.model.Task;
import com.vysh.projectmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserDashboardController {

    private final TaskService taskService;

    @GetMapping("/user/dashboard")
    public String userDashboard(Model model) {
        // 1. Get logged-in user's username
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // 2. Fetch tasks assigned to this user
        List<Task> tasks = taskService.getTasksByUsername(username);

        // 3. Add to model
        model.addAttribute("tasks", tasks);

        return "user-tasks";
    }
}
