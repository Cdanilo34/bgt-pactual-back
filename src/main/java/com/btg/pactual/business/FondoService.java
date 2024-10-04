package com.btg.pactual.business;

import java.math.BigDecimal;

public interface FondoService {
    void suscribirFondo(String idCliente, Long idFondo);
    void cancelarFondo(String clienteId, Long fondoId);
}
