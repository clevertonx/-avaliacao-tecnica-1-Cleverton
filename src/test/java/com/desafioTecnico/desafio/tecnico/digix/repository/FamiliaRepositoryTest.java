package com.desafioTecnico.desafio.tecnico.digix.repository;

import java.util.Optional;

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
    public void deve_buscar_familia_pelo_id() throws Exception {

        double rendaTotal = 1000.0;
        int quantidadeDependentes = 2;
        String nomeResponsavel = "Carlos";
        Familia familia = new Familia(rendaTotal, quantidadeDependentes, nomeResponsavel);
        familiaRepository.save(familia);

        Optional<Familia> familiaRetornada = familiaRepository.findById(familia.getId());

        Assertions.assertTrue(familiaRetornada.isPresent());
        Assertions.assertEquals(familia.getId(), familiaRetornada.get().getId());
        Assertions.assertEquals(rendaTotal, familiaRetornada.get().getRendaTotal());
        Assertions.assertEquals(quantidadeDependentes, familiaRetornada.get().getQuantidadeDependentes());
        Assertions.assertEquals(nomeResponsavel, familiaRetornada.get().getNomeResponsavel());
    }
}
