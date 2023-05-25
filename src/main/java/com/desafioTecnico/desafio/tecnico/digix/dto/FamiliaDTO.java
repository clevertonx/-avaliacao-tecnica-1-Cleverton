package com.desafioTecnico.desafio.tecnico.digix.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FamiliaDTO {
    private Double rendaTotal;
    private int quantidadeDependentes;
    private int pontuacao;


    public FamiliaDTO(Double rendaTotal, int quantidadeDependentes, int pontuacao) {
        this.rendaTotal = rendaTotal;
        this.quantidadeDependentes = quantidadeDependentes;
        this.pontuacao = pontuacao;
    }

}
