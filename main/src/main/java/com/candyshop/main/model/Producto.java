package com.candyshop.main.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long  producto_id;

    @Column(nullable = false)
    private String alergenos;
    private String descripcion;
    private String nombre;
    private double peso_gramos;
    private double precio;


}
