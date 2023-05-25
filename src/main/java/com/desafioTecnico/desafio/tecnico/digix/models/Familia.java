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

    public Familia(Double rendaTotal, int quantidadeDependentes) {
        this.rendaTotal = rendaTotal;
        this.quantidadeDependentes = quantidadeDependentes;
    }

    public int calcularPontuacao() {
        int pontuacao = 0;
    
        if (rendaTotal <= 900) {
            pontuacao += 5;
        } else if (rendaTotal <= 1500) {
            pontuacao += 3;
        }
    
        if (quantidadeDependentes >= 3) {
            pontuacao += 3;
        } else if (quantidadeDependentes >= 1) {
            pontuacao += 2;
        }
    
        return pontuacao;
    }

    public int getPontuacao() {
        return calcularPontuacao();
    }

}
