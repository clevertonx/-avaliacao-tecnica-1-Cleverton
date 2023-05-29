package com.desafioTecnico.desafio.tecnico.digix.Mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.desafioTecnico.desafio.tecnico.digix.dto.FamiliaPutDTO;
import com.desafioTecnico.desafio.tecnico.digix.dto.FamiliaRequestDTO;
import com.desafioTecnico.desafio.tecnico.digix.dto.FamiliaResponseDTO;
import com.desafioTecnico.desafio.tecnico.digix.models.Familia;

@Mapper(componentModel = "spring")
public interface FamiliaMapper {

    public Familia familiaRequestParaFamilia(FamiliaRequestDTO familiaRequestDTO) throws Exception;

    public FamiliaResponseDTO familiaParaFamiliaResponse(Familia familia);

    public List<FamiliaResponseDTO> familiasParaFamiliaResponseDTOs(List<Familia> familias);

    public Familia familiaPutParaFamilia(FamiliaPutDTO familiaPutDTO);
}
