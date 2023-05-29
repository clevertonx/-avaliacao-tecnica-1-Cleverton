package com.desafioTecnico.desafio.tecnico.digix.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.desafioTecnico.desafio.tecnico.digix.models.Familia;

public interface FamiliaRepository extends CrudRepository<Familia, Long> {

    public List<Familia> findByNomeResponsavelContainingIgnoreCase(String nomeResponsavel);

}