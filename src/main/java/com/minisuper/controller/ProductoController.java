package com.minisuper.controller;

import com.minisuper.domain.Producto;
import com.minisuper.service.CategoriaService;
import com.minisuper.service.ProductoService;
import com.minisuper.service.ProveedorService;
import jakarta.validation.Valid;
import java.util.Locale;
import java.util.Optional;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;
    private final ProveedorService proveedorService;
    private final MessageSource messageSource;

    public ProductoController(ProductoService productoService,
                              CategoriaService categoriaService,
                              ProveedorService proveedorService,
                              MessageSource messageSource) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
        this.proveedorService = proveedorService;
        this.messageSource = messageSource;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var productos = productoService.getProductos();
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("categorias", categoriaService.getCategorias());
        model.addAttribute("proveedores", proveedorService.getProveedores());
        model.addAttribute("producto", new Producto());
        return "producto/listado";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Producto producto, RedirectAttributes redirectAttributes) {
        try {
            productoService.save(producto);
            redirectAttributes.addFlashAttribute("todoOk",
                    messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el producto.");
        }
        return "redirect:/producto/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(Integer idProducto, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String detalle = "mensaje.eliminado";

        try {
            productoService.delete(idProducto);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            detalle = "El producto no existe";
        } catch (IllegalStateException e) {
            titulo = "error";
            detalle = "No se puede eliminar el producto, tiene datos asociados";
        } catch (Exception e) {
            titulo = "error";
            detalle = "Error inesperado al eliminar el producto";
        }

        redirectAttributes.addFlashAttribute(titulo, detalle);
        return "redirect:/producto/listado";
    }

    @GetMapping("/modificar/{idProducto}")
    public String modificar(@PathVariable("idProducto") Integer idProducto,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        Optional<Producto> productoOpt = productoService.getProducto(idProducto);

        if (productoOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El producto no existe");
            return "redirect:/producto/listado";
        }

        model.addAttribute("producto", productoOpt.get());
        model.addAttribute("categorias", categoriaService.getCategorias());
        model.addAttribute("proveedores", proveedorService.getProveedores());
        return "producto/modifica";
    }
}
