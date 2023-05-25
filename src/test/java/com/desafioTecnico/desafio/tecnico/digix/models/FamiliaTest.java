package com.desafioTecnico.desafio.tecnico.digix.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FamiliaTest {
    
    @Test
    public void testCalcularPontuacao() {

        double rendaTotal = 800.0;
        int quantidadeDependentes = 4;
        int pontuacaoEsperada = 8;

        Familia familia = new Familia(rendaTotal, quantidadeDependentes);

        int pontuacaoCalculada = familia.calcularPontuacao();

        Assertions.assertEquals(pontuacaoEsperada, pontuacaoCalculada);
    }

}
