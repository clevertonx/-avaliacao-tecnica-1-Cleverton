package com.desafioTecnico.desafio.tecnico.digix.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.desafioTecnico.desafio.tecnico.digix.Builders.FamiliaBuilder;
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

        Familia familiaRetornada = familiaRepository.findById(familia.getId()).orElse(null);

        assertThat(familiaRetornada).isNotNull();
        assertThat(familiaRetornada.getId()).isEqualTo(familia.getId());
        assertThat(familiaRetornada.getRendaTotal()).isEqualTo(rendaTotal);
        assertThat(familiaRetornada.getQuantidadeDependentes()).isEqualTo(quantidadeDependentes);
        assertThat(familiaRetornada.getNomeResponsavel()).isEqualTo(nomeResponsavel);
    }

    @Test
    public void deve_salvar_uma_familia() throws Exception {

        Familia familia = new FamiliaBuilder().build();

        familiaRepository.save(familia);

        Assertions.assertNotNull(familia.getId());
    }

    @Test
    public void deve_remover_familia() throws Exception {
        Familia familia = new FamiliaBuilder().build();
        familiaRepository.save(familia);

        familiaRepository.deleteById(familia.getId());

        Optional<Familia> familiaBuscada = familiaRepository.findById(familia.getId());

        Assertions.assertFalse(familiaBuscada.isPresent());
    }

    @Test
    public void deve_buscar_familia_pelo_nome_do_responsavel() throws Exception {
        String nomeResponsavel = "Margarida";
        Familia familia = new FamiliaBuilder().comNomeResponsavel(nomeResponsavel).build();
        familiaRepository.save(familia);

        List<Familia> familiaRetornada = familiaRepository.findByNomeResponsavelContainingIgnoreCase(nomeResponsavel);

        Assertions.assertTrue(familiaRetornada.contains(familia));
    }

}
