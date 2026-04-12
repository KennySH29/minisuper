
package com.minisuper.controller;

import com.minisuper.domain.Inventario;
import com.minisuper.service.InventarioService;
import com.minisuper.service.ProductoService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inventario")
public class InventarioController {

    private final InventarioService inventarioService;
    private final ProductoService productoService;
    private final MessageSource messageSource;

    public InventarioController(InventarioService inventarioService, ProductoService productoService, MessageSource messageSource) {
        this.inventarioService = inventarioService;
        this.productoService = productoService;
        this.messageSource = messageSource;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var inventarios = inventarioService.getInventarios();
        model.addAttribute("inventarios", inventarios);
        model.addAttribute("totalInventarios", inventarios.size());
        model.addAttribute("productos", productoService.getProductos());
        model.addAttribute("inventario", new Inventario());
        return "inventario/listado";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Inventario inventario, RedirectAttributes redirectAttributes) {
        inventarioService.save(inventario);
        redirectAttributes.addFlashAttribute("todoOk", messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
        return "redirect:/inventario/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idInventario, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String detalle = "mensaje.eliminado";
        try {
            inventarioService.delete(idInventario);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            detalle = "inventario.error01";
        } catch (IllegalStateException e) {
            titulo = "error";
            detalle = "inventario.error02";
        } catch (Exception e) {
            titulo = "error";
            detalle = "inventario.error03";
        }
        redirectAttributes.addFlashAttribute(titulo, messageSource.getMessage(detalle, null, Locale.getDefault()));
        return "redirect:/inventario/listado";
    }

    @GetMapping("/modificar/{idInventario}")
    public String modificar(@PathVariable("idInventario") Integer idInventario, Model model, RedirectAttributes redirectAttributes) {
        Optional<Inventario> inventarioOpt = inventarioService.getInventario(idInventario);
        if (inventarioOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", messageSource.getMessage("inventario.error01", null, Locale.getDefault()));
            return "redirect:/inventario/listado";
        }
        model.addAttribute("inventario", inventarioOpt.get());
        model.addAttribute("productos", productoService.getProductos());
        return "inventario/modifica";
    }
}
