package com.btg.pactual.business;

import com.btg.pactual.model.Transaccion;

import java.util.List;

public interface TransaccionService {
    List<Transaccion> obtenerTodasLasTransacciones();
}
