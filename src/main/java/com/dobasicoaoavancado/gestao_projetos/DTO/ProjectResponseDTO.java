package com.dobasicoaoavancado.gestao_projetos.DTO;

import jakarta.validation.Valid;

import java.util.UUID;

public record ProjectResponseDTO(@Valid UUID id, @Valid String name) {
}
