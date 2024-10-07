package com.btg.pactual.business.impl;

import com.btg.pactual.business.FondoService;
import com.btg.pactual.business.NotificacionService;
import com.btg.pactual.enums.TipoTransaccion;
import com.btg.pactual.exception.ClienteNoEncontradoException;
import com.btg.pactual.exception.FondoNoEncontradoException;
import com.btg.pactual.exception.SaldoInsuficienteException;
import com.btg.pactual.model.Cliente;
import com.btg.pactual.model.Fondo;
import com.btg.pactual.model.Transaccion;
import com.btg.pactual.repository.ClienteRepository;
import com.btg.pactual.repository.FondoRepository;
import com.btg.pactual.repository.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FondoServiceImpl implements FondoService {

    @Value("${mail.enable}")
    private boolean enableMail;
    private final FondoRepository fondoRepository;
    private final ClienteRepository clienteRepository;
    private final TransaccionRepository transaccionRepository;
    private final NotificacionService notificacionService;

    @Autowired
    public FondoServiceImpl(FondoRepository fondoRepository, ClienteRepository clienteRepository,
                            TransaccionRepository transaccionRepository,NotificacionService notificacionService) {
        this.fondoRepository = fondoRepository;
        this.clienteRepository = clienteRepository;
        this.transaccionRepository = transaccionRepository;
        this.notificacionService = notificacionService;
    }

    @Override
    public void suscribirFondo(String idCliente, Long idFondo) {
        // Obtener el cliente desde la base de datos
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ClienteNoEncontradoException("Cliente no encontrado"));

        // Obtener el fondo desde la base de datos
        Fondo fondo = fondoRepository.findById(idFondo)
                .orElseThrow(() -> new FondoNoEncontradoException("Fondo no encontrado"));

        // Verificar si el cliente tiene suficiente saldo para suscribirse al fondo
        if (cliente.getSaldo() < fondo.getMontoMinimo()) {
            throw new SaldoInsuficienteException("No tiene saldo disponible para vincularse al fondo " + fondo.getNombre());
        }


        // Descontar el monto mínimo del saldo del cliente
        cliente.setSaldo(cliente.getSaldo() - fondo.getMontoMinimo());

        // Agregar fondo a la lista de suscripciones
        if (cliente.getFondosSuscritos() == null) {
            List<Cliente.FondoSuscrito> fondsClient = new ArrayList<>();
            fondsClient.add(new Cliente.FondoSuscrito(idFondo, fondo.getNombre(), fondo.getMontoMinimo()));
            cliente.setFondosSuscritos(fondsClient);
        } else {
            cliente.getFondosSuscritos().add(new Cliente.FondoSuscrito(idFondo, fondo.getNombre(), fondo.getMontoMinimo()));
        }

        // Crear la transacción de suscripción
        Transaccion transaccion = new Transaccion();
        transaccion.setClienteId(idCliente);
        transaccion.setFondoId(idFondo);
        transaccion.setMonto(fondo.getMontoMinimo());
        transaccion.setTipoTransaccion(TipoTransaccion.SUSCRIPCION.getDescripcion());
        transaccion.setFecha(LocalDateTime.now().toString());
        transaccion.setId(UUID.randomUUID().toString());

        // Guardar la transacción y actualizar el cliente
        transaccionRepository.save(transaccion);
        clienteRepository.save(cliente);

        // Enviar notificación (email o SMS)
        if(enableMail) {
            notificacionService.enviarNotificacion(cliente, fondo, TipoTransaccion.SUSCRIPCION.getDescripcion());
        }
    }

    @Override
    public void cancelarFondo(String clienteId, Long fondoId) {
        // Obtener el cliente desde la base de datos
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNoEncontradoException("Cliente no encontrado"));

        Cliente.FondoSuscrito fondoSuscrito = cliente.getFondosSuscritos().stream()
                .filter(f -> f.getFondoId().equals(fondoId))
                .findFirst().orElseThrow(() ->  new FondoNoEncontradoException("El cliente no está suscrito a este fondo."));

        // Remover el fondo
        cliente.getFondosSuscritos().remove(fondoSuscrito);

        // Reembolsar monto
        cliente.setSaldo(cliente.getSaldo() + fondoSuscrito.getMontoVinculacion());

        // Guardar cliente actualizado
        clienteRepository.save(cliente);

        // Crear transacción
        Transaccion transaccion = new Transaccion();
        transaccion.setClienteId(clienteId);
        transaccion.setFondoId(fondoId);
        transaccion.setMonto(fondoSuscrito.getMontoVinculacion());
        transaccion.setTipoTransaccion(TipoTransaccion.CANCELACION.getDescripcion());
        transaccion.setFecha(LocalDateTime.now().toString());
        transaccion.setId(UUID.randomUUID().toString());
        transaccionRepository.save(transaccion);

    }

    @Override
    public List<Fondo> listarFondos() {
        return fondoRepository.findAll();
    }


}
