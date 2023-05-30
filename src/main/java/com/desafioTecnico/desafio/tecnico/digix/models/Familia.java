package com.desafioTecnico.desafio.tecnico.digix.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor

public class Familia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Double rendaTotal;
    private int quantidadeDependentes;
    private int pontuacao;
    private String nomeResponsavel;

    public Familia(Double rendaTotal, int quantidadeDependentes, String nomeResponsavel) {
        this.rendaTotal = rendaTotal;
        this.quantidadeDependentes = quantidadeDependentes;
        this.nomeResponsavel = nomeResponsavel;
    }
}
// O código representa uma classe chamada "Familia" que representa informações sobre uma família.
