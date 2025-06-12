package com.vysh.projectmanager.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate deadline;

    // Who is assigned this task
    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;
    

    // Who created the task (admin)
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
    
    public enum Priority {
        LOW, MEDIUM, HIGH
    }
    
    public enum Status {
        TODO, IN_PROGRESS, COMPLETED
    }
    @ManyToOne
@JoinColumn(name = "project_id")
private Project project;
    
    @Column(name = "visible_to_user")
private Boolean visibleToUser = true; // Default is true (visible)

}
