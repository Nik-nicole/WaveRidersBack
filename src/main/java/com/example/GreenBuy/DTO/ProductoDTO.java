package com.example.GreenBuy.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private String nameProducto;
    private Integer precio;
    private Integer lote;
    private Integer cantidad;
    private LocalDate fechaVencimiento; // Asegúrate de que este campo esté presente
        // Añadir este campo
}
