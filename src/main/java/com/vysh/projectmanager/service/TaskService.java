package com.vysh.projectmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
 
import com.vysh.projectmanager.model.Project;
import com.vysh.projectmanager.model.Task;
import com.vysh.projectmanager.model.Task.Status;
import com.vysh.projectmanager.model.User;
import com.vysh.projectmanager.repository.ProjectRepository;
import com.vysh.projectmanager.repository.TaskRepository;
import com.vysh.projectmanager.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public List<Task> getTasksForUser(User user) {
    return taskRepository.findByAssignedUser(user, 
        Sort.by(Sort.Order.asc("priority"), Sort.Order.asc("deadline")));
}
    public List<Task> getTasksByUsername(String username) {
    return taskRepository.findByAssignedUserUsername(username);
}

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public void updateTask(Task task) {
        taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
    public List<Task> getTasksByProjectId(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }
    public void assignTaskToUserAndProject(Task task, String username, Long projectId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found: " + projectId));

        task.setAssignedUser(user);
        task.setProject(project);
        task.setStatus(Status.TODO); // ✅ correct enum assignment
        taskRepository.save(task);
    }
    public void saveTask(Task task) {
    taskRepository.save(task);
}

}
