package com.desafioTecnico.desafio.tecnico.digix.Builders;

import com.desafioTecnico.desafio.tecnico.digix.models.Familia;

public class FamiliaBuilder {
    private Double rendaTotal = 1000.0;
    private int quantidadeDependentes = 2;
    private String nomeResponsavel = "Carlos";

    public FamiliaBuilder()
            throws Exception {
    }

    public FamiliaBuilder comRendaTotal(Double rendaTotal) {
        this.rendaTotal = rendaTotal;
        return this;
    }

    public FamiliaBuilder comQuantidadeDependentes(int quantidadeDependentes) {
        this.quantidadeDependentes = quantidadeDependentes;
        return this;
    }

    public FamiliaBuilder comNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
        return this;
    }

    public Familia build() {
        return new Familia(rendaTotal, quantidadeDependentes, nomeResponsavel);
    }
}
