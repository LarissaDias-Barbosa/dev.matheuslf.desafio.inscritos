package com.dobasicoaoavancado.gestao_projetos.repository;

import com.dobasicoaoavancado.gestao_projetos.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    Optional<Project> findByName(String name);
    Optional<Project> findById(UUID id);
}
