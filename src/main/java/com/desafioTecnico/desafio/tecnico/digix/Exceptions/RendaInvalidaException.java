package com.desafioTecnico.desafio.tecnico.digix.Exceptions;

public class RendaInvalidaException extends Exception {

    public RendaInvalidaException() {
        super("A renda total deve ser 0 ou maior!");
    }
}
