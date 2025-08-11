package com.peru.smartperu.service;

import com.peru.smartperu.model.Cliente;
import com.peru.smartperu.repository.ClienteRepository;
import com.peru.smartperu.repository.OrdenReparacionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final OrdenReparacionRepository ordenReparacionRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    // Nuevo método para buscar clientes por una palabra clave
    public List<Cliente> searchByKeyword(String keyword) {
        return clienteRepository.searchByKeyword(keyword);
    }

    public Cliente findById (Integer id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public void save(Cliente cliente) { clienteRepository.save(cliente);}

    public boolean deleteCliente(Integer idCliente) {
        // Primero, verifica si el cliente tiene asociaciones antes de intentar eliminar
        if (hasAssociatedOrdersOrPurchases(idCliente)) {
            return false;
        }

        Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);
        if (clienteOpt.isPresent()) {
            clienteRepository.deleteById(idCliente);
            return true;
        }
        return false;
    }

    public boolean hasAssociatedOrdersOrPurchases(Integer clienteId) {
        return ordenReparacionRepository.existsByClienteIdCliente(clienteId);
    }
}