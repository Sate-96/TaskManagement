package net.javaguides.task.controller;

import net.javaguides.task.dto.TaskDto;
import net.javaguides.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private TaskService taskService;

    // @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Build add task REST API
    @PostMapping
    public ResponseEntity<TaskDto> addTask(@RequestBody TaskDto taskDto){
        TaskDto savedTask = taskService.addTask(taskDto);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable("id") Long taskId){
        TaskDto taskDto = taskService.getTask(taskId);
        return new ResponseEntity<>(taskDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks(){
        List<TaskDto> tasks = taskService.getAllTasks();
//        return new ResponseEntity<>(tasks, HttpStatus.OK);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("{id}")
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto,
                                              @PathVariable("id") Long taskId) {
        TaskDto updatedTask = taskService.updateTask(taskDto, taskId);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") Long taskId){
        taskService.deleteTask(taskId);
        return ResponseEntity.ok("Task deleted successfully!.");
    }

    @PatchMapping("{id}/complete")
    public ResponseEntity<TaskDto> completeTask(@PathVariable("id") Long taskId){
        TaskDto updatedTask = taskService.completeTask(taskId);
        return ResponseEntity.ok(updatedTask);
    }

    @PatchMapping("{id}/in-complete")
    public ResponseEntity<TaskDto> inCompleteTask(@PathVariable("id") Long taskId){
        TaskDto updatedTask = taskService.inCompleteTask(taskId);
        return ResponseEntity.ok(updatedTask);
    }
}
