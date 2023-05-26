package com.desafioTecnico.desafio.tecnico.digix.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.desafioTecnico.desafio.tecnico.digix.Repository.FamiliaRepository;
import com.desafioTecnico.desafio.tecnico.digix.dto.FamiliaRequestDTO;
import com.desafioTecnico.desafio.tecnico.digix.models.Familia;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class FamiliaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FamiliaRepository familiaRepository;

    @BeforeEach
    @AfterEach
    public void deletaDados() {
        familiaRepository.deleteAll();
    }

    @Test
    public void criarFamilia_deveRetornarNovaFamiliaDTO() throws Exception {
        // Arrange
        FamiliaRequestDTO familiaDTO = new FamiliaRequestDTO(1000.0, 2);

        // Act & Assert
        mockMvc.perform(post("/familias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(familiaDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.rendaTotal").value(1000.0))
                .andExpect(jsonPath("$.quantidadeDependentes").value(2));
    }

    @Test
    public void atualizarFamilia_deveRetornarFamiliaAtualizadaDTO() throws Exception {
        // Arrange
        Familia familia = new Familia(1000.0, 2);
        familiaRepository.save(familia);

        long familiaId = familia.getId();
        FamiliaRequestDTO familiaDTO = new FamiliaRequestDTO(1500.0, 3);

        // Act & Assert
        mockMvc.perform(put("/familias/{id}", familiaId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(familiaDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.rendaTotal").value(1500.0))
                .andExpect(jsonPath("$.quantidadeDependentes").value(3));
    }

    @Test
    public void excluirFamilia_deveRetornarStatusOk() throws Exception {
        Familia familia = new Familia(1000.0, 2);
        familiaRepository.save(familia);

        long familiaId = familia.getId();

        mockMvc.perform(delete("/familias/{id}", familiaId))
                .andExpect(status().isOk());
    }

    private String asJsonString(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

}
