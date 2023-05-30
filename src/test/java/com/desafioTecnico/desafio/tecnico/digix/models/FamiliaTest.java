package com.desafioTecnico.desafio.tecnico.digix.models;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.desafioTecnico.desafio.tecnico.digix.Builders.FamiliaBuilder;

public class FamiliaTest {

    @Test
    public void deve_criar_uma_familia() throws Exception {

        Double rendaTotal = 1000.0;
        int quantidadeDependentes = 2;
        String nomeResponsavel = "Carlos";

        Familia familia = new FamiliaBuilder().build();

        assertThat(familia.getRendaTotal()).isEqualTo(rendaTotal);
        assertThat(familia.getQuantidadeDependentes()).isEqualTo(quantidadeDependentes);
        assertThat(familia.getNomeResponsavel()).isEqualTo(nomeResponsavel);
    }
}
