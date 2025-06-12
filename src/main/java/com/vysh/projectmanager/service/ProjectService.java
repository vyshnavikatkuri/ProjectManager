package com.vysh.projectmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vysh.projectmanager.model.Project;
import com.vysh.projectmanager.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public void createProject(Project project) {
        projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }
     public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}

