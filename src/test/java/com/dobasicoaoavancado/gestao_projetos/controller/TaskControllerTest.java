package com.dobasicoaoavancado.gestao_projetos.controller;

import com.dobasicoaoavancado.gestao_projetos.DTO.CreateTaskDTO;
import com.dobasicoaoavancado.gestao_projetos.DTO.UpdateTaskDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void DeveCriarTasksComSucesso() throws Exception {

      CreateTaskDTO dto = new CreateTaskDTO("task de teste",
              "todo",
              "PENDENTE",
              "ALTA",
              UUID.randomUUID());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
        )
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("String encontrada com sucesso"));
    }

    @Test
    void DeveChamarTasksQueExistem() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks")
                        .param("priority", "ALTA")
                        .param("status", "PENDENTE")
                        .param("projectId", "0000000000")
        )
        .andExpect(status().isOk());
    }

    @Test
    void DeveAtualizarStatusTask() throws Exception {
        UUID id = UUID.randomUUID();

        UpdateTaskDTO update = new UpdateTaskDTO(id, "DOING");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/tasks/" + id + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update))
        )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Task atualizada com sucesso"));
    }

    @Test
    void DeveRetornarConfliteSeTaskForAtualizada() throws Exception {
        UUID id = UUID.randomUUID();

        UpdateTaskDTO update = new UpdateTaskDTO(id, "DOING");

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/tasks/" + id + "/status")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(update))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Esta task, já foi atualizada"));
    }

    @Test
    void DeveDeletarTaskComSucesso() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/tasks/" + id)
                                .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Task, deletada com sucesso"));
    }
}
