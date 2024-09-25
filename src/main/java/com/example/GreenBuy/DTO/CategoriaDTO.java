package com.example.GreenBuy.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {
    private Long id;
    private String nameCategoria;
    private Integer puntos;

    // Constructors, getters, and setters
}