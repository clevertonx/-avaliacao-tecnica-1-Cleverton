package com.desafioTecnico.desafio.tecnico.digix.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.desafioTecnico.desafio.tecnico.digix.Repository.FamiliaRepository;
import com.desafioTecnico.desafio.tecnico.digix.dto.FamiliaDTO;
import com.desafioTecnico.desafio.tecnico.digix.models.Familia;

@Service
public class FamiliaService {

    private final FamiliaRepository familiaRepository;

    public FamiliaService(FamiliaRepository familiaRepository) {
        this.familiaRepository = familiaRepository;
    }

    public List<FamiliaDTO> listarFamiliasOrdenadasPorPontuacao() {
        List<Familia> familias = familiaRepository.findAll();

        List<FamiliaDTO> familiaDTOs = familias.stream()
                .map(familia -> new FamiliaDTO(
                        familia.getRendaTotal(),
                        familia.getQuantidadeDependentes(),
                        familia.calcularPontuacao()))
                .collect(Collectors.toList());
        familiaDTOs.sort((familia1, familia2) -> Integer.compare(familia2.getPontuacao(), familia1.getPontuacao()));
        return familiaDTOs;
    }

    public FamiliaDTO criarFamilia(FamiliaDTO familiaDTO) {
        Familia familia = new Familia(familiaDTO.getRendaTotal(), familiaDTO.getQuantidadeDependentes());
        familiaRepository.save(familia);

        return new FamiliaDTO(familia.getRendaTotal(), familia.getQuantidadeDependentes(), familia.calcularPontuacao());
    }

    public FamiliaDTO atualizarFamilia(long id, FamiliaDTO familiaDTO) {
        Familia familia = familiaRepository.findById(id);

        if (familia != null) {
            familia.setRendaTotal(familiaDTO.getRendaTotal());
            familia.setQuantidadeDependentes(familiaDTO.getQuantidadeDependentes());

            familiaRepository.save(familia);

            return new FamiliaDTO(familia.getRendaTotal(), familia.getQuantidadeDependentes(),
                    familia.calcularPontuacao());
        } else {
            return null;
        }
    }

    public void excluirFamilia(long id) {
        familiaRepository.deleteById(id);
    }
}