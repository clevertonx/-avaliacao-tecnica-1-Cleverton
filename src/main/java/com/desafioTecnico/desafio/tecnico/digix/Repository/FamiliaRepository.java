package com.desafioTecnico.desafio.tecnico.digix.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.desafioTecnico.desafio.tecnico.digix.models.Familia;

@Repository
public interface FamiliaRepository extends JpaRepository<Familia, Long> {

    Familia findById(long id);

}