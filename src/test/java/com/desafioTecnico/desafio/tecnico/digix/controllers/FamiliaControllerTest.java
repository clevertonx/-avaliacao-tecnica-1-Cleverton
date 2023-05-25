package com.desafioTecnico.desafio.tecnico.digix.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.desafioTecnico.desafio.tecnico.digix.Repository.FamiliaRepository;
import com.desafioTecnico.desafio.tecnico.digix.dto.FamiliaDTO;
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

    public List<FamiliaDTO> listarFamiliasOrdenadasPorPontuacao() {
        List<Familia> familias = familiaRepository.findAll();

        familias.forEach(Familia::calcularPontuacao); // Calcula a pontuação para cada família

        List<FamiliaDTO> familiaDTOs = familias.stream()
                .map(familia -> new FamiliaDTO(
                        familia.getRendaTotal(),
                        familia.getQuantidadeDependentes(),
                        familia.getPontuacao())) // Obtém a pontuação calculada
                .collect(Collectors.toList());

        familiaDTOs.sort((familia1, familia2) -> Integer.compare(familia2.getPontuacao(), familia1.getPontuacao()));

        return familiaDTOs;
    }

    @Test
    public void criarFamilia_deveRetornarNovaFamiliaDTO() throws Exception {
        // Arrange
        FamiliaDTO familiaDTO = new FamiliaDTO(1000.0, 2, 0);

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
        FamiliaDTO familiaDTO = new FamiliaDTO(1500.0, 3, 0);

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
