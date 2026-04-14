package com.minisuper.controller;

import com.minisuper.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/venta")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("ventas", ventaService.getVentas());
        model.addAttribute("clientes", ventaService.getClientes());
        model.addAttribute("productos", ventaService.getProductos());
        model.addAttribute("metodosPago", ventaService.getMetodosPago());
        model.addAttribute("usuarios", ventaService.getUsuarios());
        return "venta/listado";
    }

    @PostMapping("/guardar")
    public String guardar(Integer idCliente,
                          Integer idUsuario,
                          Integer idMetodoPago,
                          Integer idProducto,
                          Integer cantidad,
                          RedirectAttributes redirectAttributes) {
        try {
            ventaService.registrarVenta(idCliente, idUsuario, idMetodoPago, idProducto, cantidad);
            redirectAttributes.addFlashAttribute("todoOk", "Venta registrada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar la venta: " + e.getMessage());
        }
        return "redirect:/venta/listado";
    }
}

