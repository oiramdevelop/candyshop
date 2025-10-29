package com.candyshop.main.api;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.candyshop.main.model.Producto;
import com.candyshop.main.repository.ProductoRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/candyShop")
public class ApiCandyShopController {
    
    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    public List<Producto> getAllProductos(){
        return productoRepository.findAll();
    }

    // CORREGIDO: Usar el mismo nombre en @PathVariable
    @GetMapping("/{producto_id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable("producto_id") Long id) {
        return productoRepository.findById(id)
                .map(producto -> ResponseEntity.ok().body(producto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/crear")
    public Producto createProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    @PutMapping("/{producto_id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable("producto_id") Long id, @RequestBody Producto productoRecibido) {

        Optional<Producto> productoBD = productoRepository.findById(id);

        if (!productoBD.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        Producto producto = productoBD.get();

        // CORREGIDO: setDescripcion ahora recibe la descripción correcta
        producto.setAlergenos(productoRecibido.getAlergenos());
        producto.setDescripcion(productoRecibido.getDescripcion()); // ← CORREGIDO
        producto.setNombre(productoRecibido.getNombre());
        producto.setPeso_gramos(productoRecibido.getPeso_gramos());
        producto.setPrecio(productoRecibido.getPrecio());

        productoRepository.save(producto);
        return ResponseEntity.ok().body(producto);
    }

    
    @DeleteMapping("/{producto_id}")
    public ResponseEntity<?> deleteProducto(@PathVariable("producto_id") Long id) {
        if (!productoRepository.existsById(id))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        productoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}