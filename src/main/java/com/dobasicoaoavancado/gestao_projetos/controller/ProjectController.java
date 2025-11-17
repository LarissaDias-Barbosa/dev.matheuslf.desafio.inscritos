package com.dobasicoaoavancado.gestao_projetos.controller;

import com.dobasicoaoavancado.gestao_projetos.DTO.CreateProjectDTO;
import com.dobasicoaoavancado.gestao_projetos.DTO.ProjectResponseDTO;
import com.dobasicoaoavancado.gestao_projetos.entity.Project;
import com.dobasicoaoavancado.gestao_projetos.repository.ProjectRepository;
import com.dobasicoaoavancado.gestao_projetos.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody CreateProjectDTO dto){
        try {
           Project project = projectService.createProject(dto.name());
            return ResponseEntity.status(HttpStatus.CREATED).body("O projecto"
                    + project.getName() + "foi salvo com sucesso");
        } catch(IllegalArgumentException e){
           return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

   @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> listarProjects() {
       return ResponseEntity.ok(projectService.listarProjects());
   }
}
