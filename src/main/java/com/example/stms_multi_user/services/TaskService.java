package com.example.stms_multi_user.services;

import java.time.LocalDateTime;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.stms_multi_user.dto.TaskRequest;
import com.example.stms_multi_user.entities.Task;
import com.example.stms_multi_user.repositories.TaskRepository;
import com.example.stms_multi_user.security.SecurityUtil;

@Service
public class TaskService {
    private TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    public TaskService(TaskRepository taskRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTask(Integer id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found with id " + id));
    }

    public Task createTask(TaskRequest taskRequest) {
        Task task = modelMapper.map(taskRequest, Task.class);
        task.setCreatedDate(LocalDateTime.now());
        task.setCreatedBy(SecurityUtil.getCurrentUserEmail());
        task.setUpdateDate(LocalDateTime.now());
        task.setUpdatedBy(SecurityUtil.getCurrentUserEmail());
        return taskRepository.save(task);
    }

    public void deletTask(Integer id) {

        Task task = taskRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Task not found with id: " + id));

        String currentUserEmail = SecurityUtil.getCurrentUserEmail();
        String currentUserRole = SecurityUtil.getUserRole();

        if (task.getCreatedBy().equals(currentUserEmail) || currentUserRole.equals("ADMIN")) {
            taskRepository.deleteById(id);
        } else
            throw new RuntimeException("You are not authorize to delete this task");
    }

    public Task updateTask(Integer id, TaskRequest updateTaskRequest) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id " + id));

        String currentUserEmail = SecurityUtil.getCurrentUserEmail();
        String currentUserRole = SecurityUtil.getUserRole();

        Task updateTask = modelMapper.map(updateTaskRequest, Task.class);

        if (updateTask.getTitle() != null)
            existingTask.setTitle(updateTask.getTitle());
        if (updateTask.getDescription() != null)
            existingTask.setDescription(updateTask.getDescription());
        if (updateTask.getDescription() != null)
            existingTask.setStatus(updateTask.getStatus());

        existingTask.setUpdateDate(LocalDateTime.now());
        existingTask.setUpdatedBy(SecurityUtil.getCurrentUserEmail());

        if (existingTask.getCreatedBy().equals(currentUserEmail) || currentUserRole.equals("ADMIN")) {
            return taskRepository.save(existingTask);
        } else
            throw new RuntimeException("You are not authorize to delete this task");

        // return taskRepository.save(existingTask);
    }
}
