package com.candyshop.main.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import com.candyshop.main.model.Productos;
import com.candyshop.main.model.Proveedor;
import com.candyshop.main.repository.ProductosRepository;
import com.candyshop.main.repository.ProveedorRepository;

@Controller
@RequestMapping("")
public class mainController {
    private Logger logger = LoggerFactory.getLogger(mainController.class);
    
    @Autowired
    private ProductosRepository productosRepository;
    private ProveedorRepository proveedorRepository;
    
    @GetMapping
    public String mainPage(Model model) {
        return "main.html";
    }
    
    // Cambia esta ruta para que no entre en conflicto
    @GetMapping("/ver-productos")
    public String verProductos(Model model) {
        List<Productos> listaProductos = productosRepository.findAll();
        model.addAttribute("productos", listaProductos);
        return "listProduct.html";
    }
      @GetMapping("/ver-proveedor")
    public String verProveedores(Model model) {
        List<Proveedor> listaProveedores = proveedorRepository.findAll();
        model.addAttribute("productos", listaProveedores);
        return "listProveedores.html";
    }
}