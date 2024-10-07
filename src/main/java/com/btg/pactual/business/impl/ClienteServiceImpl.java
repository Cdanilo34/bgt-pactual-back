package com.btg.pactual.business.impl;

import com.btg.pactual.business.ClienteService;
import com.btg.pactual.model.Cliente;
import com.btg.pactual.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Cliente obtenerClientePorId(String id) {
       return clienteRepository.findById(id).get();
    }
}
