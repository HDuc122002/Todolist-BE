package com.todoList.toDoList.controller;

import com.todoList.toDoList.entity.Task;
import com.todoList.toDoList.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/{userId}")
    public List<Task> getAllTaskByUser(@PathVariable Long userId){
        return taskService.getAllTask(userId);
    }

    @PostMapping("create/{userId}")
    public ResponseEntity<?> createTask(@RequestBody Task task,
                                        @PathVariable Long userId){
        try {
            taskService.createTask(userId,task);
            return ResponseEntity.ok().body("Created task successfully");
        }catch (Exception e){
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

    @PutMapping("update/{userId}")
    public ResponseEntity<?> updateTask(@RequestBody Task updateTask,
                                        @PathVariable Long userId){
        try {
            taskService.updateTask(userId,updateTask);
            return ResponseEntity.ok().body("updated task successfully");
        }catch (Exception e){
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

    @DeleteMapping("delete/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId){
        try {
            taskService.deleteTask(taskId);
            return ResponseEntity.ok().body("Delete task successfully");
        }catch (Exception e){
            return ResponseEntity.ok().body(e.getMessage());
        }
    }
}
