package com.btg.pactual.business.impl;

import com.btg.pactual.business.ClienteService;
import com.btg.pactual.model.Cliente;
import com.btg.pactual.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Optional<Cliente> obtenerClientePorId(String id) {
        return clienteRepository.findById(id);
    }

    @Override
    public Cliente crearCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente actualizarCliente(String id, Cliente clienteActualizado) {
        if (clienteRepository.existsById(id)) {
            clienteActualizado.setId(id);
            return clienteRepository.save(clienteActualizado);
        }
        return null;
    }

    @Override
    public void eliminarCliente(String id) {
        clienteRepository.deleteById(id);
    }
}
