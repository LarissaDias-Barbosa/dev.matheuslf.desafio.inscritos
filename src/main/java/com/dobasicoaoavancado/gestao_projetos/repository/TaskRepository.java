package com.dobasicoaoavancado.gestao_projetos.repository;

import com.dobasicoaoavancado.gestao_projetos.entity.Priority;
import com.dobasicoaoavancado.gestao_projetos.entity.Status;
import com.dobasicoaoavancado.gestao_projetos.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    Optional<Task> findByName(String name);
    Optional<Task> findByTitle(String title);
    Optional<Task> findById(UUID id);

    List<Task> findByStatus(String status);
    List<Task> findByPriority(String priority);
    List<Task> findByStatusAndPriority(Status status, Priority priority);
}
