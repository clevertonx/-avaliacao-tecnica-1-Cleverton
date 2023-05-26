package com.desafioTecnico.desafio.tecnico.digix.models;

public abstract class Criterios {
    protected int pontos;

    public abstract boolean atende(Familia familia);

    public int getPontos() {
        return pontos;
    }

    public static class CritérioRendaAté900 extends Criterios {
        public CritérioRendaAté900() {
            pontos = 5;
        }
    
        @Override
        public boolean atende(Familia familia) {
            return familia.getRendaTotal() <= 900;
        }
    }

    public static class CritérioRenda901a1500 extends Criterios {
        public CritérioRenda901a1500() {
            pontos = 3;
        }
    
        @Override
        public boolean atende(Familia familia) {
            return familia.getRendaTotal() > 900 && familia.getRendaTotal() <= 1500;
        }
    }

    public static class CritérioDependentes3OuMais extends Criterios {
        public CritérioDependentes3OuMais() {
            pontos = 3;
        }
    
        @Override
        public boolean atende(Familia familia) {
            return familia.getQuantidadeDependentes() >= 3;
        }
    }

    public static class CritérioDependentes1Ou2 extends Criterios {
        public CritérioDependentes1Ou2() {
            pontos = 2;
        }
    
        @Override
        public boolean atende(Familia familia) {
            int dependentes = familia.getQuantidadeDependentes();
            return dependentes >= 1 && dependentes <= 2;
        }
    }
}
