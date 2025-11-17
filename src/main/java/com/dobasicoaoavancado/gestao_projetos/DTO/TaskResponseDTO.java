package com.dobasicoaoavancado.gestao_projetos.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.UUID;

public record TaskResponseDTO(@Valid UUID id, @Valid @NotBlank String priority, @Valid String title,
                              @Valid String description, @Valid @NotBlank String status,
                              @Valid Date dueDate, @Valid @NotNull UUID projectId) {
}
