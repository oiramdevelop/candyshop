package com.candyshop.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.candyshop.main.model.Productos;

public interface ProductosRepository extends JpaRepository< Productos ,Long>{

    
}
