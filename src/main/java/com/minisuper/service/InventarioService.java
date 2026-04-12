
package com.minisuper.service;


import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.minisuper.domain.Inventario;
import com.minisuper.repository.InventarioRepository;
import java.util.Date;

@Service
public class InventarioService {
    
    private final InventarioRepository inventarioRepository;

    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }
    
    @Transactional(readOnly = true)
    public List<Inventario > getInventarios(){
        return inventarioRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<Inventario> getInventario(Integer idInventario) {
        return inventarioRepository.findById(idInventario);
    }
    
    @Transactional
    public void save(Inventario inventario) {
        inventario.setFechaActualizacion(new Date());
        inventarioRepository.save(inventario);
    }
    
     @Transactional
    public void delete(Integer idInventario) {
        if (!inventarioRepository.existsById(idInventario)) {
            throw new IllegalArgumentException("El inventario con ID " + idInventario + " no existe.");
        }

        try {
            inventarioRepository.deleteById(idInventario);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar el inventario. Tiene datos asociados.", e);
        }
    }
}
