package com.minisuper.repository;

import com.minisuper.domain.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    
    public List<Cliente> findByNombre(String nombre);   
}
