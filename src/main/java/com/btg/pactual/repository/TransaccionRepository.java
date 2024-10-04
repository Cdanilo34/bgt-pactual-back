package com.btg.pactual.repository;

import com.btg.pactual.model.Transaccion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaccionRepository extends MongoRepository<Transaccion, String> {
    // Encuentra todas las transacciones de un cliente
    List<Transaccion> findByClienteId(String clienteId);
}
