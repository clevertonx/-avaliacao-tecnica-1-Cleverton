package com.desafioTecnico.desafio.tecnico.digix.Mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.desafioTecnico.desafio.tecnico.digix.Repository.FamiliaRepository;
import com.desafioTecnico.desafio.tecnico.digix.dto.FamiliaPutDTO;
import com.desafioTecnico.desafio.tecnico.digix.dto.FamiliaRequestDTO;
import com.desafioTecnico.desafio.tecnico.digix.dto.FamiliaResponseDTO;
import com.desafioTecnico.desafio.tecnico.digix.models.Familia;

@Component
public class FamiliaMapperImpl implements FamiliaMapper {

    @Autowired
    FamiliaRepository familiaRepository;

    @Override
    public Familia familiaRequestParaFamilia(FamiliaRequestDTO familiaRequestDTO) throws Exception {
        return new Familia(familiaRequestDTO.getRendaTotal(), familiaRequestDTO.getQuantidadeDependentes(),
                familiaRequestDTO.getNomeResponsavel());
    }

    @Override
    public FamiliaResponseDTO familiaParaFamiliaResponse(Familia familia) {
        return new FamiliaResponseDTO(familia.getRendaTotal(), familia.getQuantidadeDependentes(),
                familia.getPontuacao(), familia.getNomeResponsavel());
    }

    @Override
    public List<FamiliaResponseDTO> familiasParaFamiliaResponseDTOs(List<Familia> familias) {
        List<FamiliaResponseDTO> familiaResponseDTOs = new ArrayList<>();
        for (Familia familia : familias) {
            familiaResponseDTOs.add(familiaParaFamiliaResponse(familia));
        }
        return familiaResponseDTOs;
    }

    @Override
    public Familia familiaPutParaFamilia(FamiliaPutDTO familiaPutDTO) {
        Familia familia = familiaRepository.findById(familiaPutDTO.getId()).get();
        familia.setRendaTotal(familiaPutDTO.getRendaTotal());
        familia.setQuantidadeDependentes(familiaPutDTO.getQuantidadeDependentes());
        familia.setNomeResponsavel(familiaPutDTO.getNomeResponsavel());
        return familia;
    }
}
