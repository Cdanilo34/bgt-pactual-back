package com.btg.pactual.rest;

import com.btg.pactual.business.TransaccionService;
import com.btg.pactual.model.Transaccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/fondos")
@CrossOrigin(origins = "*")
public class TransaccionController {

    @Autowired
    private TransaccionService transaccionService;

    @GetMapping("/transacciones")
    public ResponseEntity<List<Transaccion>> listarTransacciones(){
       return ResponseEntity.ok(transaccionService.obtenerTodasLasTransacciones());
    }

}
