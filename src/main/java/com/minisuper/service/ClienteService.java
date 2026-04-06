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
        String cedula = cliente.getCedula() != null ? cliente.getCedula().trim() : null;
        String correo = cliente.getCorreo() != null ? cliente.getCorreo().trim() : null;

        if (cedula == null || cedula.isBlank()) {
            throw new IllegalArgumentException("La cédula es obligatoria.");
        }

        cliente.setCedula(cedula);

        if (correo != null && correo.isBlank()) {
            correo = null;
        }
        cliente.setCorreo(correo);

        if (cliente.getIdCliente() == null) {
            if (clienteRepository.existsByCedula(cedula)) {
                throw new IllegalArgumentException("Ya existe un cliente con esa cédula.");
            }
            if (correo != null && clienteRepository.existsByCorreo(correo)) {
                throw new IllegalArgumentException("Ya existe un cliente con ese correo.");
            }
        } else {
            if (clienteRepository.existsByCedulaAndIdClienteNot(cedula, cliente.getIdCliente())) {
                throw new IllegalArgumentException("Ya existe otro cliente con esa cédula.");
            }
            if (correo != null && clienteRepository.existsByCorreoAndIdClienteNot(correo, cliente.getIdCliente())) {
                throw new IllegalArgumentException("Ya existe otro cliente con ese correo.");
            }
        }

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
    public List<Cliente> buscarPorNombre(String nombre) {
        return clienteRepository.findByNombreContainingIgnoreCase(nombre);
    }
}
