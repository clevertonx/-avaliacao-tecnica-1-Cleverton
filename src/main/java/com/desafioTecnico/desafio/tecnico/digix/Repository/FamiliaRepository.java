package com.desafioTecnico.desafio.tecnico.digix.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.desafioTecnico.desafio.tecnico.digix.models.Familia;

public interface FamiliaRepository extends JpaRepository<Familia, Long> {

    Familia findById(long id);

}