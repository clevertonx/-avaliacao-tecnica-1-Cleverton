package com.desafioTecnico.desafio.tecnico.digix.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.desafioTecnico.desafio.tecnico.digix.models.Criterios.CritérioDependentes1Ou2;
import com.desafioTecnico.desafio.tecnico.digix.models.Criterios.CritérioDependentes3OuMais;
import com.desafioTecnico.desafio.tecnico.digix.models.Criterios.CritérioRenda901a1500;
import com.desafioTecnico.desafio.tecnico.digix.models.Criterios.CritérioRendaAté900;

@Component
public class PontuadorFamilia {
    private List<Criterios> criterios;

    public PontuadorFamilia() {
        criterios = new ArrayList<>();
        criterios.add(new CritérioRendaAté900());
        criterios.add(new CritérioRenda901a1500());
        criterios.add(new CritérioDependentes3OuMais());
        criterios.add(new CritérioDependentes1Ou2());
    }

    public void adicionarCritério(Criterios critério) {
        criterios.add(critério);
    }

    public List<Familia> obterFamíliasPontuadas(List<Familia> familias) {
        for (Familia familia : familias) {
            int pontosTotais = 0;
            for (Criterios critério : criterios) {
                if (critério.atende(familia)) {
                    pontosTotais += critério.getPontos();
                }
            }
            familia.setPontuacao(pontosTotais);
        }

        // Ordena as famílias pela pontuação de forma decrescente
        Collections.sort(familias, Comparator.comparingInt(Familia::getPontuacao).reversed());

        return familias;
    }
}
// O código representa uma classe chamada "PontuadorFamilia" que é responsável por atribuir pontos às famílias com base em critérios específicos.