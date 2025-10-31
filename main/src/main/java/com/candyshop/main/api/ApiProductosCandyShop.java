package com.candyshop.main.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.candyshop.main.model.Productos;

import com.candyshop.main.repository.ProductosRepository;

@RestController
@RequestMapping("/api/productos")
public class ApiProductosCandyShop {
    
    @Autowired
    private ProductosRepository productosRepository;

    @GetMapping
    public List<Productos> getALLProductos(){
        return productosRepository.findAll();
    }

    // CORREGIDO: Usar el mismo nombre en @PathVariable
    @GetMapping("/{productos_id}")
    public ResponseEntity<Productos> getProductoById(@PathVariable("productos_id") Long id) {
        return productosRepository.findById(id)
                .map(productos -> ResponseEntity.ok().body(productos))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/crear")
    public Productos createProducto(@RequestBody Productos productos) {
        return productosRepository.save(productos);
    }

    @PutMapping("/{porductos_id}")
    public ResponseEntity<Productos> updateProducto(@PathVariable("productos_id") Long id, @RequestBody Productos productosRecibidos) {

        Optional<Productos> productosBD = productosRepository.findById(id);

        if (!productosBD.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        Productos productos = productosBD.get();

        productos.setNombre(productosRecibidos.getNombre());
        productos.setAlergenos(productosRecibidos.getAlergenos()); 
        productos.setDescripcion(productosRecibidos.getDescripcion());
        productos.setPeso_gramos(productosRecibidos.getPeso_gramos());
        productos.setPrecio(productosRecibidos.getPrecio());
        

        productosRepository.save(productos);
        return ResponseEntity.ok().body(productos);
    }

    
    @DeleteMapping("/{productos_id}")
    public ResponseEntity<?> deleteProducto(@PathVariable("productos_id") Long id) {
        if (!productosRepository.existsById(id))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        productosRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}