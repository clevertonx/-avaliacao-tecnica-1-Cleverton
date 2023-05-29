package com.desafioTecnico.desafio.tecnico.digix.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.desafioTecnico.desafio.tecnico.digix.Repository.FamiliaRepository;
import com.desafioTecnico.desafio.tecnico.digix.Services.FamiliaService;
import com.desafioTecnico.desafio.tecnico.digix.dto.FamiliaPutDTO;
import com.desafioTecnico.desafio.tecnico.digix.dto.FamiliaRequestDTO;
import com.desafioTecnico.desafio.tecnico.digix.dto.FamiliaResponseDTO;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<List<FamiliaResponseDTO>> pontuarFamilias() {
        List<FamiliaResponseDTO> familiaDTOs = familiaService.pontuarFamilias();
        return ResponseEntity.ok(familiaDTOs);
    }

    @PostMapping
    public ResponseEntity<FamiliaResponseDTO> criarFamilia(@RequestBody @Valid FamiliaRequestDTO familiaDTO) throws Exception {
        FamiliaResponseDTO novaFamiliaDTO = familiaService.criarFamilia(familiaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaFamiliaDTO);
    }

    @PutMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<FamiliaResponseDTO> atualizarFamilia(@RequestBody @Valid FamiliaPutDTO familiaPutDTO,
            @PathVariable Long id) {

        return ResponseEntity.ok(familiaService.alterarFamilia(familiaPutDTO, id));
    }

    @DeleteMapping(path = "/{id}")
    public void excluirFamilia(@PathVariable Long id) {
        familiaRepository.deleteById(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

}
