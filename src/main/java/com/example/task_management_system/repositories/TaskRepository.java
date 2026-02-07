package com.example.task_management_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.task_management_system.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    
}
