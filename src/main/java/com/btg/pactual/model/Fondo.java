package com.btg.pactual.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "fondos")
public class Fondo {
    @Id
    private String id;
    private String nombre;
    private Integer montoMinimo;
    private String categoria;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getMontoMinimo() {
        return montoMinimo;
    }

    public void setMontoMinimo(Integer montoMinimo) {
        this.montoMinimo = montoMinimo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
