package com.candyshop.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.candyshop.main.model.Producto;

public interface ProductoRepository extends JpaRepository < Producto , Long>  {

    
} 