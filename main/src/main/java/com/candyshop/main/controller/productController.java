package com.candyshop.main.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.candyshop.main.model.Producto;
import com.candyshop.main.repository.ProductoRepository;



@Controller
@RequestMapping("/productos")
public class productController {

    private Logger logger = LoggerFactory.getLogger(productController.class);
    
    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    public String listProducts(Model model) {
        List<Producto> listProduct = productoRepository.findAll();
        model.addAttribute("productos", listProduct); // ✅ Cambiado a "productos"
        return "listProduct";
    }

    @GetMapping("/eliminar/{producto_id}")
    public String removeProduct(@PathVariable("producto_id") Long id, RedirectAttributes redAttrib) {
        if (!productoRepository.existsById(id)) {
            redAttrib.addFlashAttribute("error", "El producto no existe");
        } else {
            productoRepository.deleteById(id);
            redAttrib.addFlashAttribute("success", "Producto eliminado correctamente");
        }
        return "redirect:/productos"; // ✅ Cambiado a "/productos"
    }

    @GetMapping("/nuevo")
    public String newProduct(Model model) {
        Producto producto = new Producto();
        model.addAttribute("producto", producto); // ✅ Cambiado a "producto"
        return "nuevoProducto";
    }

    @PostMapping("/crear")
    public String createProduct(@ModelAttribute("producto") Producto producto) {
        productoRepository.save(producto);
        return "redirect:/productos"; // ✅ Cambiado a "/productos"
    }

    @GetMapping("/editar/{producto_id}")
    public String editProduct(@PathVariable("producto_id") Long id, Model model) {
        if (!productoRepository.existsById(id)) {
            return "redirect:/productos";
        }
        Producto producto = productoRepository.findById(id).get();
        model.addAttribute("producto", producto); // ✅ Cambiado a "producto"
        return "editarProducto"; // ✅ Cambiado a "editarProducto"
    }

    @PostMapping("/modificar")
    public String modifyProduct(@ModelAttribute("producto") Producto producto) {
        productoRepository.save(producto);
        return "redirect:/productos"; // ✅ Cambiado a "/productos"
    }
}