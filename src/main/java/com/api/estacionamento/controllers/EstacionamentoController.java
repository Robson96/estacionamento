package com.api.estacionamento.controllers;

import com.api.estacionamento.dtos.EstacionamentoDTO;
import com.api.estacionamento.models.EstacionamentoModel;
import com.api.estacionamento.services.EstacionamentoService;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api-estacionamento")
public class EstacionamentoController {

    final EstacionamentoService service;

    public EstacionamentoController(EstacionamentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> saveEstacionamentoVaga(@RequestBody @Valid EstacionamentoDTO dto) {
        if (service.existePlacaCarro(dto.getPlacaCarro()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ja exite um registro com essa placa de carro!");
        if (service.existeVagaNumero(dto.getVagaNumero()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ja existe um registro com essa vaga!");
        if (service.existeApartamentoEBloco(dto.getApartamento(), dto.getBloco()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ja existe um registro com esse apt e bloco!");
        var estacionamentoModel = new EstacionamentoModel();
        BeanUtils.copyProperties(dto, estacionamentoModel);
        estacionamentoModel.setRegistroData(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(estacionamentoModel));
    }

    @GetMapping
    public ResponseEntity<Page<EstacionamentoModel>> getAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> obterRegistro(@PathVariable(value = "id") UUID id) {
        if (service.findById(id).isPresent())
            return ResponseEntity.status(HttpStatus.FOUND).body(service.findById(id).get());
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRegistro(@PathVariable(value = "id") UUID id) {
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
    
}
