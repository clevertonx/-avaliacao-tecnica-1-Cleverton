package com.desafioTecnico.desafio.tecnico.digix.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.desafioTecnico.desafio.tecnico.digix.Builders.FamiliaBuilder;
import com.desafioTecnico.desafio.tecnico.digix.Repository.FamiliaRepository;
import com.desafioTecnico.desafio.tecnico.digix.dto.FamiliaRequestDTO;
import com.desafioTecnico.desafio.tecnico.digix.dto.FamiliaResponseDTO;
import com.desafioTecnico.desafio.tecnico.digix.models.Familia;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class FamiliaControllerTest {

    private ObjectMapper objectMapper = new ObjectMapper();

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
    public void deve_buscar_as_familias_cadastradas() throws Exception {
        Double rendaTotal = 1000.0;
        Double rendaTotal2 = 800.0;
        int quantidadeDependentes = 2;
        int quantidadeDependentes2 = 4;
        String nomeResponsavel = "Carlos";
        String nomeResponsavel2 = "Carla";

        ArrayList<Familia> familias = new ArrayList<Familia>();

        familias.add(new FamiliaBuilder().comRendaTotal(rendaTotal).comQuantidadeDependentes(quantidadeDependentes)
                .comNomeResponsavel(nomeResponsavel).build());

        familias.add(new FamiliaBuilder().comRendaTotal(rendaTotal2).comQuantidadeDependentes(quantidadeDependentes2)
                .comNomeResponsavel(nomeResponsavel2).build());

        familiaRepository.saveAll(familias);

        mockMvc.perform(get("/familias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rendaTotal", is(rendaTotal2)))
                .andExpect(jsonPath("$[0].quantidadeDependentes", is(quantidadeDependentes2)))
                .andExpect(jsonPath("$[0].nomeResponsavel", is(nomeResponsavel2)))
                .andExpect(jsonPath("$[1].rendaTotal", is(rendaTotal)))
                .andExpect(jsonPath("$[1].quantidadeDependentes", is(quantidadeDependentes)))
                .andExpect(jsonPath("$[1].nomeResponsavel", is(nomeResponsavel)));
    }

    @Test
    public void deve_retornar_nova_Familia() throws Exception {

        FamiliaRequestDTO familiaDTO = new FamiliaRequestDTO(1000.0, 2, "Carlos");

        mockMvc.perform(post("/familias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(familiaDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.rendaTotal").value(1000.0))
                .andExpect(jsonPath("$.quantidadeDependentes").value(2))
                .andExpect(jsonPath("$.nomeResponsavel").value("Carlos"))
                .andDo(result -> {
                    String jsonResponse = result.getResponse().getContentAsString();
                    FamiliaResponseDTO responseDTO = objectMapper.readValue(jsonResponse, FamiliaResponseDTO.class);
                    assertThat(responseDTO.getRendaTotal()).isEqualTo(1000.0);
                    assertThat(responseDTO.getQuantidadeDependentes()).isEqualTo(2);
                    assertThat(responseDTO.getNomeResponsavel()).isEqualTo("Carlos");
                });
    }

    @Test
    public void deve_retornar_familia_atualizada() throws Exception {

        Familia familia = new Familia(1000.0, 2, "Carlos");
        familiaRepository.save(familia);

        long familiaId = familia.getId();
        FamiliaRequestDTO familiaDTO = new FamiliaRequestDTO(1500.0, 3, "Joana");

        mockMvc.perform(put("/familias/{id}", familiaId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(familiaDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.rendaTotal").value(1500.0))
                .andExpect(jsonPath("$.quantidadeDependentes").value(3))
                .andExpect(jsonPath("$.nomeResponsavel").value("Joana"))
                .andDo(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    FamiliaResponseDTO familiaResponseDTO = objectMapper.readValue(responseBody,
                            FamiliaResponseDTO.class);
                    assertThat(familiaResponseDTO.getRendaTotal()).isEqualTo(1500.0);
                    assertThat(familiaResponseDTO.getQuantidadeDependentes()).isEqualTo(3);
                    assertThat(familiaResponseDTO.getNomeResponsavel()).isEqualTo("Joana");
                });
    }

    @Test
    public void deve_remover_familia_por_id() throws Exception {
        Familia familia = new Familia(1000.0, 2, "Carlos");
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
