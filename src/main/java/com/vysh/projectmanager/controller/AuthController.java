package com.vysh.projectmanager.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vysh.projectmanager.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        userService.registerUser(username, password);
        return "redirect:/login";
    }

    // ✅ Add this method to redirect user after login based on their role
    @GetMapping("/dashboard")
    public String redirectToDashboard() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String roles = auth.getAuthorities().toString();

        if (roles.contains("ROLE_ADMIN")) {
            return "redirect:/admin/dashboard";
        } else if (roles.contains("ROLE_USER")) {
            return "redirect:/user/dashboard";
        } else {
            return "redirect:/login?error";
        }
    }
     @PostMapping("/logout")
    public String logout() {
        SecurityContextHolder.clearContext(); // Clear session
        return "redirect:/login"; // Redirect to login page after logout
    }
}
