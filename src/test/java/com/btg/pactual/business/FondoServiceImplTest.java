package com.btg.pactual.business;

import com.btg.pactual.business.impl.FondoServiceImpl;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FondoServiceImplTest {

    private FondoService fondoService;
    private FondoRepository fondoRepository;
    private ClienteRepository clienteRepository;
    private TransaccionRepository transaccionRepository;

    @BeforeEach
    void setUp() {
        fondoRepository = mock(FondoRepository.class);
        clienteRepository = mock(ClienteRepository.class);
        transaccionRepository = mock(TransaccionRepository.class);
        fondoService = new FondoServiceImpl(fondoRepository, clienteRepository, transaccionRepository,null);
    }

    @Test
    void testSuscribirFondo_Success() {
        // Arrange
        String idCliente = "cliente123";
        String idFondo = "1";
        Cliente cliente = new Cliente();
        cliente.setId(idCliente);
        cliente.setSaldo(100000);
        cliente.setFondosSuscritos(new ArrayList<>());

        Fondo fondo = new Fondo();
        fondo.setId(idFondo);
        fondo.setNombre("Fondo de Prueba");
        fondo.setMontoMinimo(75000);

        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(cliente));
        when(fondoRepository.findById(Long.valueOf(idFondo))).thenReturn(Optional.of(fondo));

        // Act
        fondoService.suscribirFondo(idCliente, Long.valueOf(idFondo));

        // Assert
        assertEquals(25000, cliente.getSaldo()); // Saldo después de la suscripción
        assertEquals(1, cliente.getFondosSuscritos().size()); // Un fondo suscrito

        ArgumentCaptor<Transaccion> transaccionCaptor = ArgumentCaptor.forClass(Transaccion.class);
        verify(transaccionRepository).save(transaccionCaptor.capture());
        Transaccion transaccion = transaccionCaptor.getValue();
        assertEquals(idCliente, transaccion.getClienteId());
        assertEquals(fondo.getMontoMinimo(), transaccion.getMonto());
        assertEquals(TipoTransaccion.SUSCRIPCION.getDescripcion(), transaccion.getTipoTransaccion());
        assertNotNull(transaccion.getId());
    }

    @Test
    void testSuscribirFondo_ClienteNoEncontrado() {
        // Arrange
        String idCliente = "cliente123";
        Long idFondo = 1L;
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(ClienteNoEncontradoException.class, () -> {
            fondoService.suscribirFondo(idCliente, idFondo);
        });
        assertEquals("Cliente no encontrado", exception.getMessage());
    }

    @Test
    void testSuscribirFondo_FondoNoEncontrado() {
        // Arrange
        String idCliente = "cliente123";
        Long idFondo = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(idCliente);
        cliente.setSaldo(100000);
        cliente.setFondosSuscritos(new ArrayList<>());

        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(cliente));
        when(fondoRepository.findById(idFondo)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(FondoNoEncontradoException.class, () -> {
            fondoService.suscribirFondo(idCliente, idFondo);
        });
        assertEquals("Fondo no encontrado", exception.getMessage());
    }

    @Test
    void testSuscribirFondo_SaldoInsuficiente() {
        // Arrange
        String idCliente = "cliente123";
        String idFondo = "1";
        Cliente cliente = new Cliente();
        cliente.setId(idCliente);
        cliente.setSaldo(50000);
        cliente.setFondosSuscritos(new ArrayList<>());

        Fondo fondo = new Fondo();
        fondo.setId(idFondo);
        fondo.setNombre("Fondo de Prueba");
        fondo.setMontoMinimo(75000);

        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(cliente));
        when(fondoRepository.findById(Long.valueOf(idFondo))).thenReturn(Optional.of(fondo));

        // Act & Assert
        Exception exception = assertThrows(SaldoInsuficienteException.class, () -> {
            fondoService.suscribirFondo(idCliente, Long.valueOf(idFondo));
        });
        assertEquals("No tiene saldo disponible para vincularse al fondo Fondo de Prueba", exception.getMessage());
    }

    @Test
    void testCancelarFondo_Success() {
        // Arrange
        String clienteId = "cliente123";
        Long fondoId = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        cliente.setSaldo(100000);
        Cliente.FondoSuscrito fondoSuscrito = new Cliente.FondoSuscrito(fondoId, "Fondo de Prueba", 75000);
        cliente.setFondosSuscritos(new ArrayList<>(Collections.singletonList(fondoSuscrito)));

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

        // Act
        fondoService.cancelarFondo(clienteId, fondoId);

        // Assert
        assertEquals(175000, cliente.getSaldo()); // Saldo después del reembolso
        assertTrue(cliente.getFondosSuscritos().isEmpty()); // No hay fondos suscritos

        ArgumentCaptor<Transaccion> transaccionCaptor = ArgumentCaptor.forClass(Transaccion.class);
        verify(transaccionRepository).save(transaccionCaptor.capture());
        Transaccion transaccion = transaccionCaptor.getValue();
        assertEquals(clienteId, transaccion.getClienteId());
        assertEquals(fondoId, transaccion.getFondoId());
        assertEquals(fondoSuscrito.getMontoVinculacion(), transaccion.getMonto());
        assertEquals(TipoTransaccion.CANCELACION.getDescripcion(), transaccion.getTipoTransaccion());
        assertNotNull(transaccion.getId());
    }

    @Test
    void testCancelarFondo_ClienteNoEncontrado() {
        // Arrange
        String clienteId = "cliente123";
        Long fondoId = 1L;
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(ClienteNoEncontradoException.class, () -> {
            fondoService.cancelarFondo(clienteId, fondoId);
        });
        assertEquals("Cliente no encontrado", exception.getMessage());
    }

    @Test
    void testCancelarFondo_FondoNoSuscrito() {
        // Arrange
        String clienteId = "cliente123";
        Long fondoId = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        cliente.setSaldo(100000);
        cliente.setFondosSuscritos(new ArrayList<>());

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

        // Act & Assert
        Exception exception = assertThrows(FondoNoEncontradoException.class, () -> {
            fondoService.cancelarFondo(clienteId, fondoId);
        });
        assertEquals("El cliente no está suscrito a este fondo.", exception.getMessage());
    }

    @Test
    void testListarFondos() {
        // Arrange
        Fondo fondo1 = new Fondo();
        fondo1.setId("1");
        Fondo fondo2 = new Fondo();
        fondo2.setId("2");
        when(fondoRepository.findAll()).thenReturn(List.of(fondo1, fondo2));

        // Act
        List<Fondo> fondos = fondoService.listarFondos();

        // Assert
        assertEquals(2, fondos.size());
        assertTrue(fondos.contains(fondo1));
        assertTrue(fondos.contains(fondo2));
    }
}

