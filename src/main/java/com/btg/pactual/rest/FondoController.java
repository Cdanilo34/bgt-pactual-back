package com.btg.pactual.rest;

import com.btg.pactual.business.FondoService;
import com.btg.pactual.dto.Suscripcion;
import com.btg.pactual.model.Fondo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fondos")
@CrossOrigin(origins = "*")
public class FondoController {

    @Autowired
    private FondoService fondoService;

    @PostMapping("/suscripcion/{clienteId}/{fondoId}")
    public ResponseEntity<?> suscribirFondo(@PathVariable String clienteId, @PathVariable Long fondoId) {
        fondoService.suscribirFondo(clienteId, fondoId);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Fondo suscrito con éxito");
        response.put("status", "200");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cancelacion/{clienteId}/{fondoId}")
    public ResponseEntity<?> cancelarFondo(@PathVariable String clienteId, @PathVariable Long fondoId) {
        fondoService.cancelarFondo(clienteId, fondoId);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Cancelación exitosa");
        response.put("status", "200");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Fondo>> listarFondos(){
        return ResponseEntity.ok(fondoService.listarFondos());
    }

}
