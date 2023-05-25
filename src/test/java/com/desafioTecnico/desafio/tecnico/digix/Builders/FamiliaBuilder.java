package com.desafioTecnico.desafio.tecnico.digix.Builders;

import com.desafioTecnico.desafio.tecnico.digix.models.Familia;

public class FamiliaBuilder {
    private Double rendaTotal;
    private int quantidadeDependentes;

    public FamiliaBuilder() {
    }

    public FamiliaBuilder withRendaTotal(Double rendaTotal) {
        this.rendaTotal = rendaTotal;
        return this;
    }

    public FamiliaBuilder withQuantidadeDependentes(int quantidadeDependentes) {
        this.quantidadeDependentes = quantidadeDependentes;
        return this;
    }

    public Familia build() {
        return new Familia(rendaTotal, quantidadeDependentes);
    }
}
