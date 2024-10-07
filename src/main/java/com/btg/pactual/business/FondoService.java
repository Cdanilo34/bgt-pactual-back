package com.btg.pactual.business;

import com.btg.pactual.model.Fondo;

import java.math.BigDecimal;
import java.util.List;

public interface FondoService {
    void suscribirFondo(String idCliente, Long idFondo);
    void cancelarFondo(String clienteId, Long fondoId);
    List<Fondo> listarFondos();
}
