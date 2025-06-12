package com.vysh.projectmanager.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vysh.projectmanager.model.Task;
import com.vysh.projectmanager.model.User;

public interface TaskRepository extends JpaRepository<Task, Long> {
     List<Task> findByAssignedUser(User user, Sort sort);
     List<Task> findByProjectId(Long projectId); 
     List<Task> findByAssignedUserUsername(String username);
}
