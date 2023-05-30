package com.desafioTecnico.desafio.tecnico.digix.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.desafioTecnico.desafio.tecnico.digix.Mappers.FamiliaMapper;
import com.desafioTecnico.desafio.tecnico.digix.Repository.FamiliaRepository;
import com.desafioTecnico.desafio.tecnico.digix.Services.FamiliaService;
import com.desafioTecnico.desafio.tecnico.digix.dto.FamiliaPutDTO;
import com.desafioTecnico.desafio.tecnico.digix.dto.FamiliaRequestDTO;
import com.desafioTecnico.desafio.tecnico.digix.dto.FamiliaResponseDTO;
import com.desafioTecnico.desafio.tecnico.digix.models.Familia;
import com.desafioTecnico.desafio.tecnico.digix.models.PontuadorFamilia;

@SpringBootTest
public class FamiliaServiceTest {

    private FamiliaRepository familiaRepository = mock(FamiliaRepository.class);
    private PontuadorFamilia pontuadorFamilia = mock(PontuadorFamilia.class);
    private FamiliaMapper familiaMapper = mock(FamiliaMapper.class);

    private FamiliaService familiaService = new FamiliaService(familiaRepository, pontuadorFamilia, familiaMapper);

    @BeforeEach
    @AfterEach
    public void deletaDados() {
        familiaRepository.deleteAll();
    }

    @Test
    void deve_pontuar_familias() {

        List<Familia> familias = Arrays.asList(
                new Familia(1000.0, 2, "Carlos"),
                new Familia(1500.0, 3, "Joana"));

        when(familiaRepository.findAll()).thenReturn(familias);
        when(pontuadorFamilia.obterFamíliasPontuadas(familias)).thenReturn(familias);

        List<FamiliaResponseDTO> resultado = familiaService.pontuarFamilias();

        assertThat(resultado)
                .isNotEmpty()
                .hasSize(2);

        verify(familiaRepository).findAll();
        verify(pontuadorFamilia).obterFamíliasPontuadas(familias);
    }

    @Test
    void deve_criar_familia_e_retornar_familia_response_dto() throws Exception {

        FamiliaRequestDTO familiaDTO = new FamiliaRequestDTO(1000.0, 2, "Carlos");
        Familia familiaSalva = new Familia(1000.0, 2, "Carlos");
        FamiliaResponseDTO familiaResponseDTO = new FamiliaResponseDTO(1000.0, 2, 0, "Carlos");

        when(familiaRepository.save(any(Familia.class))).thenReturn(familiaSalva);
        when(pontuadorFamilia.obterFamíliasPontuadas(Collections.singletonList(familiaSalva)))
                .thenReturn(Collections.singletonList(familiaSalva));

        FamiliaResponseDTO resultado = familiaService.criarFamilia(familiaDTO);

        assertThat(resultado).isEqualTo(familiaResponseDTO);

        verify(familiaRepository).save(any(Familia.class));
        verify(pontuadorFamilia).obterFamíliasPontuadas(Collections.singletonList(familiaSalva));
    }

    @Test
    void deve_alterar_familia_e_retornar_familia_response_dto() {

        long familiaId = 1;
        FamiliaPutDTO familiaPutDTO = new FamiliaPutDTO(familiaId, 2000.0, 3, "João");
        Familia familiaExistente = new Familia(1500.0, 2, "Carlos");
        Familia familiaAlterada = new Familia(2000.0, 3, "João");
        FamiliaResponseDTO familiaResponseDTO = new FamiliaResponseDTO(2000.0, 3, 0, "João");

        when(familiaRepository.findById(familiaId)).thenReturn(Optional.of(familiaExistente));
        when(familiaRepository.save(any(Familia.class))).thenReturn(familiaAlterada);
        when(familiaMapper.familiaParaFamiliaResponse(familiaAlterada)).thenReturn(familiaResponseDTO);

        FamiliaResponseDTO resultado = familiaService.alterarFamilia(familiaPutDTO, familiaId);

        assertThat(resultado).isEqualTo(familiaResponseDTO);

        verify(familiaRepository).findById(familiaId);
        verify(familiaRepository).save(familiaAlterada);
        verify(familiaMapper).familiaParaFamiliaResponse(familiaAlterada);
    }

    @Test
    void deve_excluir_familia_pelo_id() {

        long familiaId = 1;

        familiaService.excluirFamilia(familiaId);

        verify(familiaRepository).deleteById(familiaId);
    }

    @Test
    void deve_criar_familia_response_dto_corretamente() {

        Familia familia = new Familia(1000.0, 2, "Carlos");

        FamiliaResponseDTO resultado = familiaService.createFamiliaResponseDTO(familia);

        assertThat(resultado.getRendaTotal()).isEqualTo(familia.getRendaTotal());
        assertThat(resultado.getQuantidadeDependentes()).isEqualTo(familia.getQuantidadeDependentes());
        assertThat(resultado.getPontuacao()).isEqualTo(familia.getPontuacao());
        assertThat(resultado.getNomeResponsavel()).isEqualTo(familia.getNomeResponsavel());
    }

    @Test
    void deve_testar_calculo_pontuacao_com_renda_900_e_dependentes_3() {

        PontuadorFamilia pontuadorFamilia = new PontuadorFamilia();

        FamiliaRequestDTO familiaRequestDTO = new FamiliaRequestDTO(900.0, 3, "João");
        Familia familia = new Familia(familiaRequestDTO.getRendaTotal(), familiaRequestDTO.getQuantidadeDependentes(),
                familiaRequestDTO.getNomeResponsavel());
        List<Familia> familias = Collections.singletonList(familia);

        List<Familia> familiasPontuadas = pontuadorFamilia.obterFamíliasPontuadas(familias);

        assertEquals(8, familiasPontuadas.get(0).getPontuacao());
    }

    @Test
    void deve_testar_calculo_pontuacao_com_renda_900_e_dependentes_2() {
        PontuadorFamilia pontuadorFamilia = new PontuadorFamilia();

        FamiliaRequestDTO familiaRequestDTO = new FamiliaRequestDTO(900.0, 2, "João");
        Familia familia = new Familia(familiaRequestDTO.getRendaTotal(), familiaRequestDTO.getQuantidadeDependentes(),
                familiaRequestDTO.getNomeResponsavel());
        List<Familia> familias = Collections.singletonList(familia);

        List<Familia> familiasPontuadas = pontuadorFamilia.obterFamíliasPontuadas(familias);

        assertEquals(7, familiasPontuadas.get(0).getPontuacao());
    }

    @Test
    void deve_testar_calculo_pontuacao_com_renda_900_e_dependentes_0() {
        PontuadorFamilia pontuadorFamilia = new PontuadorFamilia();

        FamiliaRequestDTO familiaRequestDTO = new FamiliaRequestDTO(900.0, 0, "João");
        Familia familia = new Familia(familiaRequestDTO.getRendaTotal(), familiaRequestDTO.getQuantidadeDependentes(),
                familiaRequestDTO.getNomeResponsavel());
        List<Familia> familias = Collections.singletonList(familia);

        List<Familia> familiasPontuadas = pontuadorFamilia.obterFamíliasPontuadas(familias);

        assertEquals(5, familiasPontuadas.get(0).getPontuacao());
    }

    @Test
    void deve_testar_calculo_pontuacao_com_renda_901_e_dependentes_3() {
        PontuadorFamilia pontuadorFamilia = new PontuadorFamilia();

        FamiliaRequestDTO familiaRequestDTO = new FamiliaRequestDTO(901.0, 3, "João");
        Familia familia = new Familia(familiaRequestDTO.getRendaTotal(), familiaRequestDTO.getQuantidadeDependentes(),
                familiaRequestDTO.getNomeResponsavel());
        List<Familia> familias = Collections.singletonList(familia);

        List<Familia> familiasPontuadas = pontuadorFamilia.obterFamíliasPontuadas(familias);

        assertEquals(6, familiasPontuadas.get(0).getPontuacao());
    }

    @Test
    void deve_testar_calculo_pontuacao_com_renda_901_e_dependentes_2() {
        PontuadorFamilia pontuadorFamilia = new PontuadorFamilia();

        FamiliaRequestDTO familiaRequestDTO = new FamiliaRequestDTO(901.0, 2, "João");
        Familia familia = new Familia(familiaRequestDTO.getRendaTotal(), familiaRequestDTO.getQuantidadeDependentes(),
                familiaRequestDTO.getNomeResponsavel());
        List<Familia> familias = Collections.singletonList(familia);

        List<Familia> familiasPontuadas = pontuadorFamilia.obterFamíliasPontuadas(familias);

        assertEquals(5, familiasPontuadas.get(0).getPontuacao());
    }

    @Test
    void deve_testar_calculo_pontuacao_com_renda_901_e_dependentes_0() {
        PontuadorFamilia pontuadorFamilia = new PontuadorFamilia();

        FamiliaRequestDTO familiaRequestDTO = new FamiliaRequestDTO(901.0, 0, "João");
        Familia familia = new Familia(familiaRequestDTO.getRendaTotal(), familiaRequestDTO.getQuantidadeDependentes(),
                familiaRequestDTO.getNomeResponsavel());
        List<Familia> familias = Collections.singletonList(familia);

        List<Familia> familiasPontuadas = pontuadorFamilia.obterFamíliasPontuadas(familias);

        assertEquals(3, familiasPontuadas.get(0).getPontuacao());
    }

    @Test
    void deve_testar_calculo_pontuacao_com_renda_1501_e_dependentes_3() {
        PontuadorFamilia pontuadorFamilia = new PontuadorFamilia();

        FamiliaRequestDTO familiaRequestDTO = new FamiliaRequestDTO(1501.0, 3, "João");
        Familia familia = new Familia(familiaRequestDTO.getRendaTotal(), familiaRequestDTO.getQuantidadeDependentes(),
                familiaRequestDTO.getNomeResponsavel());
        List<Familia> familias = Collections.singletonList(familia);

        List<Familia> familiasPontuadas = pontuadorFamilia.obterFamíliasPontuadas(familias);

        assertEquals(3, familiasPontuadas.get(0).getPontuacao());
    }

    @Test
    void deve_testar_calculo_pontuacao_com_renda_1501_e_dependentes_2() {
        PontuadorFamilia pontuadorFamilia = new PontuadorFamilia();

        FamiliaRequestDTO familiaRequestDTO = new FamiliaRequestDTO(1501.0, 2, "João");
        Familia familia = new Familia(familiaRequestDTO.getRendaTotal(), familiaRequestDTO.getQuantidadeDependentes(),
                familiaRequestDTO.getNomeResponsavel());
        List<Familia> familias = Collections.singletonList(familia);

        List<Familia> familiasPontuadas = pontuadorFamilia.obterFamíliasPontuadas(familias);

        assertEquals(2, familiasPontuadas.get(0).getPontuacao());
    }

    @Test
    void deve_testar_calculo_pontuacao_com_renda_1501_e_dependentes_0() {
        PontuadorFamilia pontuadorFamilia = new PontuadorFamilia();

        FamiliaRequestDTO familiaRequestDTO = new FamiliaRequestDTO(1501.0, 0, "João");
        Familia familia = new Familia(familiaRequestDTO.getRendaTotal(), familiaRequestDTO.getQuantidadeDependentes(),
                familiaRequestDTO.getNomeResponsavel());
        List<Familia> familias = Collections.singletonList(familia);

        List<Familia> familiasPontuadas = pontuadorFamilia.obterFamíliasPontuadas(familias);

        assertEquals(0, familiasPontuadas.get(0).getPontuacao());
    }
}
