package com.btg.pactual.enums;

public enum TipoTransaccion {
    SUSCRIPCION("Suscripción"),
    CANCELACION("Cancelación");

    private final String descripcion;

    TipoTransaccion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
