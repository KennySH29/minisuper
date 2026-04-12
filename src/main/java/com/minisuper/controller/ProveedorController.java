package com.minisuper.controller;

import com.minisuper.domain.Proveedor;
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
@RequestMapping("/proveedor")
public class ProveedorController {

    private final ProveedorService proveedorService;
    private final MessageSource messageSource;

    public ProveedorController(ProveedorService proveedorService, MessageSource messageSource) {
        this.proveedorService = proveedorService;
        this.messageSource = messageSource;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var proveedor = proveedorService.getProveedores();
        model.addAttribute("proveedores", proveedor);
        model.addAttribute("totalProveedores", proveedor.size());
        return "proveedor/listado";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Proveedor proveedor, RedirectAttributes redirectAttributes) {
        try {
            proveedorService.save(proveedor);
            redirectAttributes.addFlashAttribute("todoOk",
                    messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el provedor.");
        }
        return "redirect:/proveedor/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(Integer idProveedor, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String detalle = "mensaje.eliminado";

        try {
            proveedorService.delete(idProveedor);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            detalle = "El proveedor no existe";
        } catch (IllegalStateException e) {
            titulo = "error";
            detalle = "No se puede eliminar el proveedor, tiene datos asociados";
        } catch (Exception e) {
            titulo = "error";
            detalle = "Error inesperado al eliminar el proveedor";
        }

        redirectAttributes.addFlashAttribute(titulo, detalle);
        return "redirect:/proveedor/listado";
    }

    @GetMapping("/modificar/{idProveedor}")
    public String modificar(@PathVariable("idProveedor") Integer idProveedor,
            Model model,
            RedirectAttributes redirectAttributes) {
        Optional<Proveedor> proveedorOpt = proveedorService.getProveedor(idProveedor);

        if (proveedorOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El proveedor no existe");
            return "redirect:/proveedor/listado";
        }
        model.addAttribute("proveedor", proveedorOpt.get());
        return "proveedor/modifica";
    }
}
