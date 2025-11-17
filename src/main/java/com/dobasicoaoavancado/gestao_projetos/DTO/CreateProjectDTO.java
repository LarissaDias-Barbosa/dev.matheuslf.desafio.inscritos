package com.dobasicoaoavancado.gestao_projetos.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record CreateProjectDTO(@NotBlank @Valid String name) {
}
