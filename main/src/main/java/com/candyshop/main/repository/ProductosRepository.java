package com.candyshop.main.repository;

import com.candyshop.main.model.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductosRepository extends JpaRepository<Productos, Long> {

    @Query(value = "SELECT * FROM productos WHERE proveedor_id = :proveedorId", nativeQuery = true)
    List<Productos> findByProveedorId(@Param("proveedorId") Long proveedorId);
}
