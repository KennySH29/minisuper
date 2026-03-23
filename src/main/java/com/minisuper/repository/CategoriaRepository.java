
package com.minisuper.repository;


import com.minisuper.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author salaz
 */
@Repository //Definios esta interfaz como un repositorio    
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> { 
    
}
