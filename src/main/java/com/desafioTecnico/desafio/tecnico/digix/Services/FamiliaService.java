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
    public final PontuadorFamilia pontuadorFamilia;
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
                pontuadorFamilia.obterFamíliasPontuadas(Collections.singletonList(familia));
                familiaRepository.save(familia);

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
// O código representa uma classe chamada "FamiliaService" que fornece serviços relacionados à entidade "Familia".
// A classe é anotada com "@Service", indicando que é uma classe de serviço do Spring.
// Possui uma dependência com o repositório "FamiliaRepository", o pontuador de famílias "PontuadorFamilia" e o mapeador "FamiliaMapper".
// Possui métodos para realizar operações como pontuar famílias, criar uma nova família, alterar uma família existente e excluir uma família.
// O método "pontuarFamilias" recupera todas as famílias do repositório, pontua as famílias usando o pontuador de famílias e retorna uma lista de objetos "FamiliaResponseDTO" contendo as informações pontuadas das famílias.
// O método "criarFamilia" cria uma nova instância de "Familia" com base nos dados fornecidos, pontua a família usando o pontuador de famílias, salva a família no repositório e retorna um objeto "FamiliaResponseDTO" representando a família criada.
// O método "alterarFamilia" altera uma família existente com base no ID fornecido. Ele recupera a família do repositório, atualiza os dados com base no objeto "FamiliaPutDTO", salva as alterações no repositório e retorna um objeto "FamiliaResponseDTO" representando a família alterada.
// O método "excluirFamilia" exclui uma família com base no ID fornecido.
// O método "createFamiliaResponseDTO" cria um objeto "FamiliaResponseDTO" com base nos dados de uma instância de "Familia".
// Essa classe encapsula a lógica de negócio relacionada à entidade "Familia" e interage com o repositório para realizar operações de criação, alteração, exclusão e pontuação das famílias.