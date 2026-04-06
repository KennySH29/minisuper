package com.minisuper.repository;

import com.minisuper.domain.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByNombreContainingIgnoreCase(String nombre);

    boolean existsByCedula(String cedula);

    boolean existsByCedulaAndIdClienteNot(String cedula, Integer idCliente);

    boolean existsByCorreo(String correo);

    boolean existsByCorreoAndIdClienteNot(String correo, Integer idCliente);
}
