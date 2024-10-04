package com.btg.pactual.business;

import com.btg.pactual.model.Cliente;

import java.util.Optional;

public interface ClienteService {
    Optional<Cliente> obtenerClientePorId(String id);
    Cliente crearCliente(Cliente cliente);
    Cliente actualizarCliente(String id, Cliente clienteActualizado);
    void eliminarCliente(String id);
}
