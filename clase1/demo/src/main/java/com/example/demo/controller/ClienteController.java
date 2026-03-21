package com.example.demo.controller;

import com.example.demo.model.Cliente;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ClienteController {
    // no usar, solo a fines didacticos, para mostrar el uso de ArrayList
    ArrayList<Cliente> clientes = new ArrayList<>();
    public ClienteController() {
        clientes.add(new Cliente(1, "Juan","1-5"));
        clientes.add(new Cliente(2, "Maria","34562"));
    }
    @GetMapping("/clientes")
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }
}
