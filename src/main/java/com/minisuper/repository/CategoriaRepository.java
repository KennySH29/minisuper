package com.minisuper.repository;

import com.minisuper.domain.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    List<Categoria> findByNombreContainingIgnoreCase(String nombre);

    boolean existsByNombre(String nombre);

    boolean existsByNombreAndIdCategoriaNot(String nombre, Integer idCategoria);
}
