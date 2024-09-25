package com.example.GreenBuy.DTO;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor



public class LoteDTO {
    private Long id;
    private LocalDate fechaVencimiento;
    private Integer cantidadTotal;
    private Integer cantidadDisponible;
    private Long productoId;

    // Constructors, getters, and setters
}
