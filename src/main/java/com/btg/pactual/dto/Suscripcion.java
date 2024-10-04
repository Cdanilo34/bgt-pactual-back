package com.btg.pactual.dto;

import java.math.BigDecimal;

public class Suscripcion {
    Long idFondo;
    String idCliente;
    BigDecimal monto;

    public Long getIdFondo() {
        return idFondo;
    }

    public void setIdFondo(Long idFondo) {
        this.idFondo = idFondo;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
}
