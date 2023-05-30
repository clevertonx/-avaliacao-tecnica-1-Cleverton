package com.desafioTecnico.desafio.tecnico.digix.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamiliaRequestDTO {

    @NotNull(message = "Renda total obrigatória!")
    private Double rendaTotal;
    private int quantidadeDependentes;
    @NotBlank(message = "Nome do responsável é obrigatório!")
    private String nomeResponsavel;
}
