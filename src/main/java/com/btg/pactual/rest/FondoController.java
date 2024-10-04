package com.btg.pactual.rest;

import com.btg.pactual.business.impl.FondoServiceImpl;
import com.btg.pactual.dto.Suscripcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fondos")
public class FondoController {

    @Autowired
    private FondoServiceImpl fondoServiceImpl;

    @PostMapping("/suscripcion")
    public ResponseEntity<String> suscribirFondo(@RequestBody Suscripcion suscripcion) {
        fondoServiceImpl.suscribirFondo(suscripcion.getIdCliente(), suscripcion.getIdFondo());
        return ResponseEntity.ok("Fondo suscrito con éxito");
    }

    @PostMapping("/cancelacion/{clienteId}/{fondoId}")
    public ResponseEntity<String> cancelarFondo(@PathVariable String clienteId, @PathVariable Long fondoId) {
        fondoServiceImpl.cancelarFondo(clienteId, fondoId);
        return ResponseEntity.ok("Cancelación exitosa");
    }

}
