package com.minisuper.service;

import com.minisuper.domain.Cliente;
import com.minisuper.repository.ClienteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional(readOnly = true)
    public List<Cliente> getClientes() {
        return clienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Cliente> getCliente(Integer idCliente) {
        return clienteRepository.findById(idCliente);
    }

    @Transactional
    public void save(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    @Transactional
    public void delete(Integer idCliente) {
        if (!clienteRepository.existsById(idCliente)) {
            throw new IllegalArgumentException("El cliente con ID " + idCliente + " no existe.");
        }
        try {
            clienteRepository.deleteById(idCliente);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar el cliente. Tiene datos asociados.", e);
        }
    }
    
    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNombre(String nombre){
        return clienteRepository.findByNombre(nombre);
    }
}
