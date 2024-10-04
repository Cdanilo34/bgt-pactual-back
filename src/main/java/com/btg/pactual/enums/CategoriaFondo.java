package com.btg.pactual.enums;

public enum CategoriaFondo {
    FPV("Fondo Voluntario de Pensión"),
    FIC("Fondo de Inversión Colectiva");

    private final String descripcion;

    CategoriaFondo(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
