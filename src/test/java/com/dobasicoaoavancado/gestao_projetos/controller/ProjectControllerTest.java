package com.dobasicoaoavancado.gestao_projetos.controller;

import com.dobasicoaoavancado.gestao_projetos.DTO.CreateProjectDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void DeveCriarProjetoComSucesso() throws Exception {

        CreateProjectDTO dto = new CreateProjectDTO("Projeto teste");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
        )
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("Projeto teste")));
    }

    @Test
    void DeveListarProjetos() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
