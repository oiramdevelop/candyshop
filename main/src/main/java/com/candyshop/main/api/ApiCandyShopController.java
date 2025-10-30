package com.candyshop.main.api;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.candyshop.main.model.Proveedor;
import com.candyshop.main.repository.ProveedorRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/proveedores")
public class ApiCandyShopController {
    
    @Autowired
    private ProveedorRepository proveedorRepository;

    @GetMapping
    public List<Proveedor> getlALLProveedor(){
        return proveedorRepository.findAll();
    }

    // CORREGIDO: Usar el mismo nombre en @PathVariable
    @GetMapping("/{proveedor_id}")
    public ResponseEntity<Proveedor> getProductoById(@PathVariable("proveedor_id") Long id) {
        return proveedorRepository.findById(id)
                .map(proveedor -> ResponseEntity.ok().body(proveedor))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/crear")
    public Proveedor createProducto(@RequestBody Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    @PutMapping("/{proveedor_id}")
    public ResponseEntity<Proveedor> updateProducto(@PathVariable("proveedor_id") Long id, @RequestBody Proveedor proveedorRecibido) {

        Optional<Proveedor> proveedorBD = proveedorRepository.findById(id);

        if (!proveedorBD.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        Proveedor proveedor = proveedorBD.get();

        // CORREGIDO: setDescripcion ahora recibe la descripción correcta
        proveedor.setNombre(proveedorRecibido.getNombre());
        proveedor.setContacto(proveedorRecibido.getContacto()); // ← CORREGIDO
        proveedor.setTelefono(proveedorRecibido.getTelefono());
        proveedor.setEmail(proveedorRecibido.getEmail());
        

        proveedorRepository.save(proveedor);
        return ResponseEntity.ok().body(proveedor);
    }

    
    @DeleteMapping("/{proveedor_id}")
    public ResponseEntity<?> deleteProducto(@PathVariable("proveedor_id") Long id) {
        if (!proveedorRepository.existsById(id))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        proveedorRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}