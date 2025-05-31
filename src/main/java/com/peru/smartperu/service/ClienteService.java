package com.peru.smartperu.service;


import com.peru.smartperu.model.Cliente;
import com.peru.smartperu.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();

    }
    public Cliente findById (Integer id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public void save(Cliente cliente) { clienteRepository.save(cliente);
    }
}