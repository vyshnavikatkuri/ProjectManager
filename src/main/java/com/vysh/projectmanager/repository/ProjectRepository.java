package com.vysh.projectmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vysh.projectmanager.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}

