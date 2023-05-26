package com.desafioTecnico.desafio.tecnico.digix.Services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.desafioTecnico.desafio.tecnico.digix.Repository.FamiliaRepository;
import com.desafioTecnico.desafio.tecnico.digix.dto.FamiliaRequestDTO;
import com.desafioTecnico.desafio.tecnico.digix.dto.FamiliaResponseDTO;
import com.desafioTecnico.desafio.tecnico.digix.models.Familia;
import com.desafioTecnico.desafio.tecnico.digix.models.PontuadorFamilia;

@Service
public class FamiliaService {

    private final FamiliaRepository familiaRepository;
    private final PontuadorFamilia pontuadorFamilia;

    public FamiliaService(FamiliaRepository familiaRepository, PontuadorFamilia pontuadorFamilia) {
        this.familiaRepository = familiaRepository;
        this.pontuadorFamilia = pontuadorFamilia;
    }

    public List<FamiliaResponseDTO> pontuarFamilias() {
        List<Familia> familias = familiaRepository.findAll();

        List<Familia> familiasPontuadas = pontuadorFamilia.obterFamíliasPontuadas(familias);

        List<FamiliaResponseDTO> familiaDTOs = familiasPontuadas.stream()
                .map(this::createFamiliaResponseDTO)
                .collect(Collectors.toList());

        return familiaDTOs;
    }

    public FamiliaResponseDTO criarFamilia(FamiliaRequestDTO familiaDTO) {
        Familia familia = new Familia(familiaDTO.getRendaTotal(), familiaDTO.getQuantidadeDependentes());
        familiaRepository.save(familia);
        pontuadorFamilia.obterFamíliasPontuadas(Collections.singletonList(familia));

        return createFamiliaResponseDTO(familia);
    }

    public FamiliaResponseDTO atualizarFamilia(long id, FamiliaRequestDTO familiaDTO) {
        Familia familia = familiaRepository.findById(id);

        if (familia != null) {
            familia.setRendaTotal(familiaDTO.getRendaTotal());
            familia.setQuantidadeDependentes(familiaDTO.getQuantidadeDependentes());

            familiaRepository.save(familia);

            return createFamiliaResponseDTO(familia);
        } else {
            return null;
        }
    }

    public void excluirFamilia(long id) {
        familiaRepository.deleteById(id);
    }

    private FamiliaResponseDTO createFamiliaResponseDTO(Familia familia) {
        return new FamiliaResponseDTO(
                familia.getRendaTotal(),
                familia.getQuantidadeDependentes(),
                familia.getPontuacao());
    }

}