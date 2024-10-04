package com.btg.pactual.repository;

import com.btg.pactual.model.Fondo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FondoRepository extends MongoRepository<Fondo, Long> {
}
