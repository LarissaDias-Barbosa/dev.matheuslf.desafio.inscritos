package com.dobasicoaoavancado.gestao_projetos.service;

import com.dobasicoaoavancado.gestao_projetos.DTO.CreateTaskDTO;
import com.dobasicoaoavancado.gestao_projetos.DTO.TaskResponseDTO;
import com.dobasicoaoavancado.gestao_projetos.entity.Priority;
import com.dobasicoaoavancado.gestao_projetos.entity.Project;
import com.dobasicoaoavancado.gestao_projetos.entity.Status;
import com.dobasicoaoavancado.gestao_projetos.entity.Task;
import com.dobasicoaoavancado.gestao_projetos.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;

    TaskService taskService;

    @BeforeEach
    void SetUp(){
       taskService = new TaskService(taskRepository);
    }

    @Test
    void DeveCriarTaskComValoresCorretos(){
        TaskRepository mockRepository = Mockito.mock(TaskRepository.class);
        TaskService service = new TaskService(mockRepository);

        CreateTaskDTO dto = new CreateTaskDTO(
                "Aprender testes unitários",
                "estudar a metodologia AAA",
                "HIGH",
                "TODO",
                UUID.randomUUID()
        );

        service.createTask(dto);

        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        verify(mockRepository, times(1)).save(captor.capture());

        Task taskSalva = captor.getValue();

        assertEquals("Aprender testes unitários", taskSalva.getTitle());
        assertEquals("Estudar a metodologia AAA", taskSalva.getDescription());
        assertEquals(Priority.HIGH, taskSalva.getPriority());
        assertEquals(Status.TODO, taskSalva.getStatus());
    }

    @Test
    void DeveBuscarTaskComValoresCorretos(){
        Project project = new Project();
       UUID projectId = UUID.randomUUID();
       project.setId(projectId);

       Task task1 = new Task();
       task1.setId(UUID.randomUUID());
       task1.setPriority(Priority.MEDIUM);
       task1.setTitle("Estudar Dtos");
       task1.setDescription("Aprender como se comportam os Dtos");
       task1.setStatus(Status.TODO);
       task1.setDueDate(new Date());
       task1.setProject(project);

       Task task2 = new Task();
       task1.setId(UUID.randomUUID());
       task1.setPriority(Priority.HIGH);
       task1.setTitle("Estudar testes unitários");
       task1.setDescription("Aprender testes unitários melhores");
       task1.setStatus(Status.DONE);
       task1.setDueDate(new Date());
       task1.setProject(project);

       when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

       List<TaskResponseDTO> resultado = taskService.listarTasks("HIGH", "TODO", projectId);

       assertEquals(1, resultado.size());
       TaskResponseDTO dto = resultado.get(0);
       assertEquals("Tarefa A", dto.title());
       assertEquals("HIGH", dto.priority());
       assertEquals("TODO", dto.description());

       verify(taskRepository, times(1)).findAll();
    }

    @Test
    void DeveAtualizarStatusQuandoForDiferente(){
        UUID id = UUID.randomUUID();
        Task task = new Task();
        task.setId(id);
        task.setStatus(Status.TODO);

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));

        boolean resultado = taskService.taskStatusId(id, "DONE");

        assertTrue(resultado);
        assertEquals(Status.DONE, task.getStatus());
        verify(taskRepository).save(task);
    }

    @Test
    void DeveRetornarVerdadeiroQuandoTarefaExistenteForDeletada(){
       UUID id = UUID.randomUUID();
       Task task = new Task();
       task.setId(id);

       when(taskRepository.findById(id)).thenReturn(Optional.of(task));

       taskService.deleteTaskById(id);

       verify(taskRepository).delete(task);
    }

    @Test
    void DeveLancarExcecaoQuandoTarefaNaoEncontrada() {
        UUID id = UUID.randomUUID();

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            taskService.deleteTaskById(id);
        });

        assertEquals("Esta task" + id + "não foi encontrada", exception.getMessage());
        verify(taskRepository, never()).delete(any());
    }
}
