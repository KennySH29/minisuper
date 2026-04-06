package com.minisuper.repository;

import com.minisuper.domain.Proveedor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
    List<Proveedor> findByNombreContainingIgnoreCase(String nombre);
}
