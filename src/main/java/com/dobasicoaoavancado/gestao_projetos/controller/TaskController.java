package com.dobasicoaoavancado.gestao_projetos.controller;

import com.dobasicoaoavancado.gestao_projetos.DTO.CreateTaskDTO;
import com.dobasicoaoavancado.gestao_projetos.DTO.TaskResponseDTO;
import com.dobasicoaoavancado.gestao_projetos.DTO.UpdateTaskDTO;
import com.dobasicoaoavancado.gestao_projetos.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/tasks")
    public ResponseEntity<?> createTask(@RequestBody CreateTaskDTO task) {
        taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body("tarefa criada com sucesso");
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponseDTO>> listarTasks(
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) UUID projectId) {

        return ResponseEntity.ok(taskService.listarTasks(priority, status, projectId));
    }

    @PutMapping("tasks/{id}/status")
    public ResponseEntity<String> taskStatusId(@PathVariable UUID id,
                                          @RequestBody UpdateTaskDTO body) {
        boolean status = taskService.taskStatusId(id, body.status());
        if (status) {
            System.out.println("Task, foi atualizada");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("esta task, já foi atualizada");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Tarefa atualizada com sucesso!");
    }


    @DeleteMapping("tasks/{id}")
    public ResponseEntity<?> deleteTaskId(@PathVariable UUID id){
        taskService.deleteTaskById(id);
        return ResponseEntity.status(HttpStatus.OK).body("task deletada com sucesso!");
    }
}
