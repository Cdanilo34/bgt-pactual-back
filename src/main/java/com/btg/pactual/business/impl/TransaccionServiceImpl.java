package com.btg.pactual.business.impl;

import com.btg.pactual.business.TransaccionService;
import com.btg.pactual.model.Transaccion;
import com.btg.pactual.repository.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransaccionServiceImpl implements TransaccionService {

    @Autowired
    TransaccionRepository transaccionRepository;

    @Override
    public List<Transaccion> obtenerTodasLasTransacciones() {
        return transaccionRepository.findAll();
    }

}
