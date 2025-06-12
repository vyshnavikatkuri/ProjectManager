package com.vysh.projectmanager.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vysh.projectmanager.model.Project;
import com.vysh.projectmanager.service.ProjectService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    // @GetMapping("/new")
    // public String showCreateForm(Model model) {
    //     model.addAttribute("project", new Project());
    //     return "create_project";
    // }

    @PostMapping("/create")
    public String createProject(@ModelAttribute Project project) {
        projectService.createProject(project);
        return "redirect:/projects/list";
    }
    @GetMapping("/create-form")
   public String showCreateProjectForm(Model model) {
    model.addAttribute("project", new Project());
    return "create-project";  // This corresponds to create_project.html
}
    @GetMapping("/list")
    public String listProjects(Model model) {
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "admin-dashboard";
    }
    @PostMapping("/{id}/delete")
public String deleteProject(@PathVariable Long id) {
    projectService.deleteProject(id);
    return "redirect:/projects/list";  // Redirect to the project list after deletion
}

}
