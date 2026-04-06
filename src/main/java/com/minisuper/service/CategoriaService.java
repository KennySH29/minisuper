package com.minisuper.service;

import com.minisuper.domain.Categoria;
import com.minisuper.repository.CategoriaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<Categoria> getCategorias() {
        return categoriaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Categoria> getCategoria(Integer idCategoria) {
        return categoriaRepository.findById(idCategoria);
    }

    @Transactional
    public void save(Categoria categoria) {
        String nombre = categoria.getNombre() != null ? categoria.getNombre().trim() : null;

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la categoría es obligatorio.");
        }

        categoria.setNombre(nombre);

        if (categoria.getIdCategoria() == null) {
            if (categoriaRepository.existsByNombre(nombre)) {
                throw new IllegalArgumentException("Ya existe una categoría con ese nombre.");
            }
        } else {
            if (categoriaRepository.existsByNombreAndIdCategoriaNot(nombre, categoria.getIdCategoria())) {
                throw new IllegalArgumentException("Ya existe otra categoría con ese nombre.");
            }
        }

        categoriaRepository.save(categoria);
    }

    @Transactional
    public void delete(Integer idCategoria) {
        if (!categoriaRepository.existsById(idCategoria)) {
            throw new IllegalArgumentException("La categoría con ID " + idCategoria + " no existe.");
        }

        try {
            categoriaRepository.deleteById(idCategoria);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar la categoría. Tiene datos asociados.", e);
        }
    }

    @Transactional(readOnly = true)
    public List<Categoria> buscarPorNombre(String nombre) {
        return categoriaRepository.findByNombreContainingIgnoreCase(nombre);
    }
}
