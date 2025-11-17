package com.dobasicoaoavancado.gestao_projetos.service;

import com.dobasicoaoavancado.gestao_projetos.DTO.ProjectResponseDTO;
import com.dobasicoaoavancado.gestao_projetos.entity.Project;
import com.dobasicoaoavancado.gestao_projetos.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(String name) {
        if(projectRepository.findByName(name).isPresent()){
            throw new RuntimeException("Pojeto salvo com sucesso");
        }

        Project project = new Project(name);
        return projectRepository.save(project);
    }

    public List<ProjectResponseDTO> listarProjects(){
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(p -> new ProjectResponseDTO(p.getId(), p.getName()))
                .toList();
    }
}
