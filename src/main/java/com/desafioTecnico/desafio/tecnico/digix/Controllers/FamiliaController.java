package com.desafioTecnico.desafio.tecnico.digix.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.desafioTecnico.desafio.tecnico.digix.Repository.FamiliaRepository;
import com.desafioTecnico.desafio.tecnico.digix.Services.FamiliaService;
import com.desafioTecnico.desafio.tecnico.digix.dto.FamiliaDTO;

import java.util.List;

@RestController
@RequestMapping("/familias")
public class FamiliaController {

    @Autowired
    FamiliaRepository familiaRepository;

    @Autowired
    FamiliaService familiaService;

    public FamiliaController(FamiliaService familiaService) {
        this.familiaService = familiaService;
    }

    @GetMapping
    public ResponseEntity<List<FamiliaDTO>> listarFamiliasOrdenadasPorPontuacao() {
        List<FamiliaDTO> familiaDTOs = familiaService.listarFamiliasOrdenadasPorPontuacao();
        return ResponseEntity.ok(familiaDTOs);
    }

    @PostMapping
    public ResponseEntity<FamiliaDTO> criarFamilia(@RequestBody FamiliaDTO familiaDTO) {
        FamiliaDTO novaFamiliaDTO = familiaService.criarFamilia(familiaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaFamiliaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FamiliaDTO> atualizarFamilia(@PathVariable("id") long id,
            @RequestBody FamiliaDTO familiaDTO) {
        FamiliaDTO familiaAtualizadaDTO = familiaService.atualizarFamilia(id, familiaDTO);
        if (familiaAtualizadaDTO != null) {
            return ResponseEntity.ok(familiaAtualizadaDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{id}")
    public void excluirFamilia(@PathVariable Long id) {
        familiaRepository.deleteById(id);
    }

}
