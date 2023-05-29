package com.desafioTecnico.desafio.tecnico.digix.dto;

import com.desafioTecnico.desafio.tecnico.digix.models.Familia;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamiliaPutDTO {

    private Long id;
    @NotNull(message = "Renda total obrigatória!")
    private Double rendaTotal;
    private int quantidadeDependentes;
    @NotBlank(message = "Nome do responsável é obrigatório!")
    private String nomeResponsavel;

    public FamiliaPutDTO(Familia familia) {
        this.id = familia.getId();
        this.rendaTotal = familia.getRendaTotal();
        this.quantidadeDependentes = familia.getQuantidadeDependentes();
        this.nomeResponsavel = familia.getNomeResponsavel();
    }
    

}
