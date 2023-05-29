package com.desafioTecnico.desafio.tecnico.digix.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.desafioTecnico.desafio.tecnico.digix.Repository.FamiliaRepository;
import com.desafioTecnico.desafio.tecnico.digix.Services.FamiliaService;
import com.desafioTecnico.desafio.tecnico.digix.dto.FamiliaRequestDTO;
import com.desafioTecnico.desafio.tecnico.digix.models.Familia;

@SpringBootTest
public class FamiliaServiceTest {

    @Autowired
    FamiliaService familiaService;

    @Autowired
    FamiliaRepository familiaRepository;

    @BeforeEach
    public void setup() {
        // Inicializa a instância de FamiliaService manualmente
        familiaService = new FamiliaService(familiaRepository);
    }

    @Test
    public void testCalcularPontuacao() {
        Familia familia1 = new Familia(1000.0, 2);
        Familia familia2 = new Familia(1500.0, 3);
        Familia familia3 = new Familia(2000.0, 1);

        int pontuacaoFamilia1 = familia1.calcularPontuacao();
        int pontuacaoFamilia2 = familia2.calcularPontuacao();
        int pontuacaoFamilia3 = familia3.calcularPontuacao();

        Assertions.assertEquals(5, pontuacaoFamilia1);
        Assertions.assertEquals(6, pontuacaoFamilia2);
        Assertions.assertEquals(2, pontuacaoFamilia3);
    }

    @Test
    public void testListarFamiliasOrdenadasPorPontuacao() {
        Familia familia1 = new Familia(1000.0, 2);
        Familia familia2 = new Familia(1500.0, 3);
        Familia familia3 = new Familia(2000.0, 1);

        int pontuacaoFamilia1 = familia1.calcularPontuacao();
        int pontuacaoFamilia2 = familia2.calcularPontuacao();
        int pontuacaoFamilia3 = familia3.calcularPontuacao();

        List<FamiliaRequestDTO> expectedFamiliaDTOs = new ArrayList<>();
        expectedFamiliaDTOs.add(new FamiliaRequestDTO(1500.0, 3, pontuacaoFamilia2));
        expectedFamiliaDTOs.add(new FamiliaRequestDTO(1000.0, 2, pontuacaoFamilia1));
        expectedFamiliaDTOs.add(new FamiliaRequestDTO(2000.0, 1, pontuacaoFamilia3));

        familiaRepository.save(familia1);
        familiaRepository.save(familia2);
        familiaRepository.save(familia3);

        List<FamiliaRequestDTO> atualFamiliaDTOs = familiaService.listarFamiliasOrdenadasPorPontuacao();

        Assertions.assertEquals(expectedFamiliaDTOs, atualFamiliaDTOs);
    }
}