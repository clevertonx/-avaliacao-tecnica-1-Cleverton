package com.desafioTecnico.desafio.tecnico.digix.Exceptions;

public class QuantidadeDeDependentesInvalidaException extends Exception {
    
    public QuantidadeDeDependentesInvalidaException() {
        super("A quantidade de dependentes deve ser 0 ou maior!");
    }
}
