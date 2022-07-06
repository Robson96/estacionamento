package com.api.estacionamento.repos;

import com.api.estacionamento.models.EstacionamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EstacionamentoRepository extends JpaRepository<EstacionamentoModel, UUID> {
    boolean existsByPlacaCarro(String placaCarro);

    boolean existsByVagaNumero(String vagaNumero);

    boolean existsByApartamentoAndBloco(String apartamento, String Bloco);


}
