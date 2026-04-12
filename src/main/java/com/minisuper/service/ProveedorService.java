package com.minisuper.service;

import com.minisuper.repository.*;
import com.minisuper.domain.Proveedor;
import com.minisuper.repository.ProveedorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    @Transactional(readOnly = true)
    public List<Proveedor> getProveedores() {
        return proveedorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Proveedor> getProveedor(Integer idProveedor) {
        return proveedorRepository.findById(idProveedor);
    }

    @Transactional
    public void save(Proveedor proveedor) {
        proveedorRepository.save(proveedor);
    }

    @Transactional
    public void delete(Integer idProveedor) {
        if (!proveedorRepository.existsById(idProveedor)) {
            throw new IllegalArgumentException("El proveedor con ID " + idProveedor + " no existe.");
        }

        try {
            proveedorRepository.deleteById(idProveedor);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar el proveedor. Tiene datos asociados.", e);
        }
    }

    @Transactional(readOnly = true)
    public List<Proveedor> buscarPorNombre(String nombreEmpresa) {
        return proveedorRepository.findByNombreEmpresaContainingIgnoreCase(nombreEmpresa);
    }
}
