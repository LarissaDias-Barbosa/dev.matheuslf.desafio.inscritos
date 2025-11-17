package com.dobasicoaoavancado.gestao_projetos.service;

import com.dobasicoaoavancado.gestao_projetos.DTO.CreateTaskDTO;
import com.dobasicoaoavancado.gestao_projetos.DTO.TaskResponseDTO;
import com.dobasicoaoavancado.gestao_projetos.entity.Priority;
import com.dobasicoaoavancado.gestao_projetos.entity.Status;
import com.dobasicoaoavancado.gestao_projetos.entity.Task;
import com.dobasicoaoavancado.gestao_projetos.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void createTask(CreateTaskDTO dto) {
        Task task = new Task();
        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setPriority(Priority.valueOf(dto.priority().toUpperCase()));
        task.setStatus(Status.valueOf(dto.status().toUpperCase()));

        taskRepository.save(task);
    }


    public List<TaskResponseDTO> listarTasks(String status, String priority, UUID projectId) {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .filter(task -> status == null || task.getStatus().name().equalsIgnoreCase(status))
                .filter(task -> priority == null || task.getPriority().name().equalsIgnoreCase(priority))
                .filter(task -> projectId == null || task.getProject().getId().equals(projectId))

                .map(task -> new TaskResponseDTO(
                        task.getId(),
                        task.getPriority().name(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getStatus().name(),
                        task.getDueDate(),
                        task.getProject().getId()
                        ))
                .toList();
    }

    public boolean taskStatusId(UUID id, String status){
        Task task = taskRepository.findById(id).orElseThrow(()-> new RuntimeException("Task, não encontrada"));

           if(task.getStatus().equals(status)){
               return false;
           }
           task.setStatus(Status.valueOf(status));
           taskRepository.save(task);
           return true;
    }


    public void deleteTaskById(UUID id){
       Task task = taskRepository.findById(id).orElseThrow(() ->
               new RuntimeException("Esta task" + id + "não foi encontrada"));
       taskRepository.delete(task);
    }
}
