package com.btg.pactual.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "clientes")
public class Cliente {
    @Id
    private String id;
    private String nombre;
    private List<FondoSuscrito> fondosSuscritos;
    private Integer saldo;
    private String notificacionPreferida;
    private String email;
    private String telefono;

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

    public List<FondoSuscrito> getFondosSuscritos() {
        return fondosSuscritos;
    }

    public void setFondosSuscritos(List<FondoSuscrito> fondosSuscritos) {
        this.fondosSuscritos = fondosSuscritos;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

    public String getNotificacionPreferida() {
        return notificacionPreferida;
    }

    public void setNotificacionPreferida(String notificacionPreferida) {
        this.notificacionPreferida = notificacionPreferida;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public static class FondoSuscrito {
        private Long fondoId;
        private String nombreFondo;
        private Integer montoVinculacion;

        public FondoSuscrito(Long fondoId, String nombreFondo, Integer montoVinculacion) {
            this.fondoId = fondoId;
            this.nombreFondo = nombreFondo;
            this.montoVinculacion = montoVinculacion;
        }

        // Getters y Setters

        public Long getFondoId() {
            return fondoId;
        }

        public void setFondoId(Long fondoId) {
            this.fondoId = fondoId;
        }

        public String getNombreFondo() {
            return nombreFondo;
        }

        public void setNombreFondo(String nombreFondo) {
            this.nombreFondo = nombreFondo;
        }

        public Integer getMontoVinculacion() {
            return montoVinculacion;
        }

        public void setMontoVinculacion(Integer montoVinculacion) {
            this.montoVinculacion = montoVinculacion;
        }

    }
}
