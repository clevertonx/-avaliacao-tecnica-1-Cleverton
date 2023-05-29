package com.desafioTecnico.desafio.tecnico.digix.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.desafioTecnico.desafio.tecnico.digix.Builders.FamiliaBuilder;

public class FamiliaTest {

    @Test
    public void deve_criar_uma_familia() throws Exception {

        Double rendaTotal = 1000.0;
        int quantidadeDependentes = 2;
        String nomeResponsavel = "Carlos";

        Familia familia = new FamiliaBuilder().build();

        Assertions.assertEquals(rendaTotal, familia.getRendaTotal());
        Assertions.assertEquals(quantidadeDependentes, familia.getQuantidadeDependentes());
        Assertions.assertEquals(nomeResponsavel, familia.getNomeResponsavel());
    }
}
