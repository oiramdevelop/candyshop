package com.candyshop.main.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Productos {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long producto_id;

    @Column(nullable = false)
    private String nombre;

    private String alergenos;
    private String descripcion;
    
    private double peso_gramos;
    private double precio;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", nullable = true) // Columna de clave foránea
    @JsonBackReference
    private Proveedor proveedor;


    

}
