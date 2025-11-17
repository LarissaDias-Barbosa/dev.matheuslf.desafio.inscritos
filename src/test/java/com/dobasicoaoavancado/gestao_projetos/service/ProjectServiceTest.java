package com.dobasicoaoavancado.gestao_projetos.service;

import com.dobasicoaoavancado.gestao_projetos.DTO.ProjectResponseDTO;
import com.dobasicoaoavancado.gestao_projetos.entity.Project;
import com.dobasicoaoavancado.gestao_projetos.repository.ProjectRepository;
import com.dobasicoaoavancado.gestao_projetos.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    ProjectRepository projectRepository;

    @InjectMocks
    ProjectService projectService;

    @Test
    void DeveCriarProjetoComSucesso(){
        String nomeProjeto = "Sistema de gestão";
        Project project = new Project(nomeProjeto);

        when(projectRepository.findByName(nomeProjeto)).thenReturn(Optional.empty());
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Project resultado = projectService.createProject(nomeProjeto);

        assertNotNull(resultado);
        assertEquals(nomeProjeto, resultado.getName());
        verify(projectRepository).save(any(Project.class));
    }

    @Test
    void DeveLancarExcecaoQuandoNomeJaExistir() {
        String nomeProjeto = "Sistema de gestão";
        Project projectExistente = new Project(nomeProjeto);

        when(projectRepository.findByName(nomeProjeto)).thenReturn(Optional.of(projectExistente));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            projectService.createProject(nomeProjeto);
        });

        assertEquals("Projeto salvo com sucesso", ex.getMessage());
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    void DeveListarTodosOsProjetos(){
        Project p1 = new Project("Sistema escolar");
        Project p2 = new Project("API pagamentos");
        p1.setId(UUID.randomUUID());
        p2.setId(UUID.randomUUID());

        when(projectRepository.findAll()).thenReturn(List.of(p1, p2));

        List<ProjectResponseDTO> resultado = projectService.listarProjects();

        assertEquals(2, resultado.size());
        assertEquals("Sistema escolar", resultado.get(0).name());
        assertEquals("API pagamentos", resultado.get(1).name());
        verify(projectRepository).findAll();
    }
}
