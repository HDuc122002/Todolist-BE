package com.todoList.toDoList.service;

import com.todoList.toDoList.entity.Task;
import com.todoList.toDoList.entity.Users;
import com.todoList.toDoList.repository.TaskRepository;
import com.todoList.toDoList.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {


    private final TaskRepository taskRepository;
    private final UserRepository  userRepository;

    public List<Task> getAllTask(Long userId){
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found by ID: "+userId));
        return taskRepository.findByUserId(userId);
    }

    public void createTask(Long userId, Task task){
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found by ID: "+userId));
        task.setUser(user);
        taskRepository.save(task);
    }

    @Transactional
    public void updateTask(Long userId, Task updateTask){
        Users existingUser = userRepository.findById(userId)
                        .orElseThrow(()->new EntityNotFoundException("User not found by ID: "+userId));
        Task task = taskRepository.findById(updateTask.getId()).orElseThrow();
        if (!updateTask.getId().equals(task.getId())){
            throw new EntityNotFoundException("Task not found");
        }

        updateTask.setTitle(updateTask.getTitle());
        updateTask.setDescription(updateTask.getDescription());
        updateTask.setStatus(updateTask.getStatus());
        updateTask.setCreateAt(task.getCreateAt());
        updateTask.setUpdatedAt(updateTask.getUpdatedAt());
        updateTask.setUser(existingUser);

        taskRepository.save(updateTask);
    }

    public void deleteTask(Long taskId){
        taskRepository.deleteById(taskId);
    }
}
