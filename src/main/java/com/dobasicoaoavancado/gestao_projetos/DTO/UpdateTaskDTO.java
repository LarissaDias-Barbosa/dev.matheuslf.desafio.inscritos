package com.dobasicoaoavancado.gestao_projetos.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateTaskDTO(@Valid UUID taskId, @NotNull(message = "Status, é obrigatória atualizar") String status) {
}
