package com.desafioTecnico.desafio.tecnico.digix.Services;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.desafioTecnico.desafio.tecnico.digix.Mappers.FamiliaMapper;
import com.desafioTecnico.desafio.tecnico.digix.Repository.FamiliaRepository;
import com.desafioTecnico.desafio.tecnico.digix.dto.FamiliaPutDTO;
import com.desafioTecnico.desafio.tecnico.digix.dto.FamiliaRequestDTO;
import com.desafioTecnico.desafio.tecnico.digix.dto.FamiliaResponseDTO;
import com.desafioTecnico.desafio.tecnico.digix.models.Familia;
import com.desafioTecnico.desafio.tecnico.digix.models.PontuadorFamilia;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FamiliaService {

    private final FamiliaRepository familiaRepository;
    private final PontuadorFamilia pontuadorFamilia;
    private final FamiliaMapper familiaMapper;

    public List<FamiliaResponseDTO> pontuarFamilias() {
        List<Familia> familias = (List<Familia>) familiaRepository.findAll();

        List<Familia> familiasPontuadas = pontuadorFamilia.obterFamíliasPontuadas(familias);

        List<FamiliaResponseDTO> familiaDTOs = familiasPontuadas.stream()
                .map(this::createFamiliaResponseDTO)
                .collect(Collectors.toList());

        return familiaDTOs;
    }

    public FamiliaResponseDTO criarFamilia(FamiliaRequestDTO familiaDTO) throws Exception {
        Familia familia = new Familia(familiaDTO.getRendaTotal(), familiaDTO.getQuantidadeDependentes(),
                familiaDTO.getNomeResponsavel());
        familiaRepository.save(familia);
        pontuadorFamilia.obterFamíliasPontuadas(Collections.singletonList(familia));

        return createFamiliaResponseDTO(familia);
    }

    public FamiliaResponseDTO alterarFamilia(FamiliaPutDTO familiaPutDTO, long id) {
        Optional<Familia> familiaOptional = familiaRepository.findById(id);
        if (familiaOptional.isEmpty()) {
            throw new NoSuchElementException();
        }
        Familia familia = familiaOptional.get();
        familia.setRendaTotal(familiaPutDTO.getRendaTotal());
        familia.setQuantidadeDependentes(familiaPutDTO.getQuantidadeDependentes());
        familia.setNomeResponsavel(familiaPutDTO.getNomeResponsavel());
        familiaRepository.save(familia);

        return familiaMapper.familiaParaFamiliaResponse(familia);
    }

    public void excluirFamilia(long id) {
        familiaRepository.deleteById(id);
    }

    public FamiliaResponseDTO createFamiliaResponseDTO(Familia familia) {
        return new FamiliaResponseDTO(
                familia.getRendaTotal(),
                familia.getQuantidadeDependentes(),
                familia.getPontuacao(),
                familia.getNomeResponsavel());
    }

}