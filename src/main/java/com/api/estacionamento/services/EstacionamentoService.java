package com.api.estacionamento.services;

import com.api.estacionamento.models.EstacionamentoModel;
import com.api.estacionamento.repos.EstacionamentoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EstacionamentoService {

    final EstacionamentoRepository repository;

    public EstacionamentoService(EstacionamentoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public EstacionamentoModel save(EstacionamentoModel estacionamentoModel) {
        return repository.save(estacionamentoModel);
    }

    public boolean existePlacaCarro(String placaCarro) {
        return repository.existsByPlacaCarro(placaCarro);
    }

    public boolean existeVagaNumero(String vagaNumero) {
        return repository.existsByVagaNumero(vagaNumero);
    }

    public boolean existeApartamentoEBloco(String apartamento, String bloco) {
        return repository.existsByApartamentoAndBloco(apartamento, bloco);
    }

    public Page<EstacionamentoModel> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<EstacionamentoModel> findById(UUID uuid) {
        return repository.findById(uuid);
    }

    @Transactional
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
