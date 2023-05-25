package com.desafioTecnico.desafio.tecnico.digix.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.desafioTecnico.desafio.tecnico.digix.Repository.FamiliaRepository;
import com.desafioTecnico.desafio.tecnico.digix.models.Familia;

@DataJpaTest
public class FamiliaRepositoryTest {

@Autowired
private FamiliaRepository familiaRepository;

    @Test
    public void deve_buscar_familia_pelo_id() {

        double rendaTotal = 1000.0;
        int quantidadeDependentes = 2;
        Familia familia = new Familia(rendaTotal, quantidadeDependentes);
        familiaRepository.save(familia);

        Familia familiaRetornada = familiaRepository.findById(familia.getId());

        Assertions.assertNotNull(familiaRetornada);
        Assertions.assertEquals(familia.getId(), familiaRetornada.getId());
        Assertions.assertEquals(rendaTotal, familiaRetornada.getRendaTotal());
        Assertions.assertEquals(quantidadeDependentes, familiaRetornada.getQuantidadeDependentes());
    }
}
