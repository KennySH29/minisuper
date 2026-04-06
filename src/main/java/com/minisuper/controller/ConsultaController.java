package com.minisuper.controller;

import com.minisuper.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/consultas")
public class ConsultaController {

    private final ClienteService clienteService;

    public ConsultaController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var clientes = clienteService.getClientes();
        model.addAttribute("clientes", clientes);
        model.addAttribute("totalClientes", clientes.size());
        model.addAttribute("cliente", null);
        return "cliente/listado";
    }

    @PostMapping("/consultaDerivada")
    public String buscarPorNombre(@RequestParam String nombre, Model model) {
        var clientes = clienteService.buscarPorNombre(nombre);
        model.addAttribute("clientes", clientes);
        model.addAttribute("totalClientes", clientes.size());
        model.addAttribute("nombreBuscado", nombre);
        model.addAttribute("cliente", null);
        return "cliente/listado";
    }
}
