package com.example.GreenBuy.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List; // Importa la clase List


@Entity
@Table(name = "productos")
@Getter
@CrossOrigin(origins = "http://localhost:5174")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_producto", length = 255)
    private String nameProducto;

    @Column(name = "precio")
    private Integer precio;

    @Column(name = "Lote")
    private Integer lote;

    @Column(name = "cantidad", length = 255)
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Column(name = "fecha_ven")
    private LocalDate Fechavencimiento;


    // Getters and setters
}
