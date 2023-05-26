package com.desafioTecnico.desafio.tecnico.digix.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FamiliaRequestDTO {
    private Double rendaTotal;
    private int quantidadeDependentes;


    public FamiliaRequestDTO(Double rendaTotal, int quantidadeDependentes) {
        this.rendaTotal = rendaTotal;
        this.quantidadeDependentes = quantidadeDependentes;
    }

}
