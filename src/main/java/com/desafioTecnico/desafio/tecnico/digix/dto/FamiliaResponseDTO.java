package com.desafioTecnico.desafio.tecnico.digix.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamiliaResponseDTO {

    private Double rendaTotal;
    private int quantidadeDependentes;
    private int pontuacao;
    private String nomeResponsavel;

}
