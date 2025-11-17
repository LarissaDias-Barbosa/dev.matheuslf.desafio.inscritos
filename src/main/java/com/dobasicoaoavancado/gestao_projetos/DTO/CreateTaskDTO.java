package com.dobasicoaoavancado.gestao_projetos.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateTaskDTO(@Valid @NotBlank String title, String description, @Valid @NotBlank String status,
                            @Valid @NotBlank String priority, @NotNull UUID projectId) {
}
