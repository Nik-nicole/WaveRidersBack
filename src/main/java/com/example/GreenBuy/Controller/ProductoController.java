package com.example.GreenBuy.Controller;

import com.example.GreenBuy.DTO.ProductoDTO;
import com.example.GreenBuy.Entities.Producto;
import com.example.GreenBuy.Services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getById(@PathVariable Long id) {
        Producto producto = productoService.getById(id);
        return producto != null ? ResponseEntity.ok(convertToDTO(producto)) : ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductoDTO>> findAll() {
        List<Producto> productos = productoService.findAll();
        List<ProductoDTO> productoDTOs = productos.stream()
                .map(this::convertToDTO)
                .toList(); // Ensure you're using Java 11 or higher for this syntax
        return ResponseEntity.ok(productoDTOs);
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> createProducto(@RequestBody ProductoDTO productoDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Set creation and update timestamps


            // Convert DTO to entity
            Producto producto = convertToEntity(productoDTO);
            productoService.save(producto);

            response.put("message", "Producto creado exitosamente");
            response.put("producto", productoDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Error procesando la solicitud: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            Producto producto = productoService.getById(id);
            if (producto == null) {
                return ResponseEntity.notFound().build();
            }

            // Update timestamps and other fields


            // Convert DTO to entity and update
            producto = convertToEntity(productoDTO);
            producto.setId(id); // Ensure the ID is set for the update
            productoService.save(producto);

            response.put("message", "Producto actualizado exitosamente");
            response.put("producto", productoDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Error procesando la solicitud: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        Producto producto = productoService.getById(id);
        if (producto != null) {
            productoService.delete(producto);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Conversion methods
    private Producto convertToEntity(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setNameProducto(productoDTO.getNameProducto());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setLote(productoDTO.getLote());
        producto.setCantidad(productoDTO.getCantidad());
        producto.setFechavencimiento(productoDTO.getFechaVencimiento());

        return producto;
    }

    private ProductoDTO convertToDTO(Producto producto) {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setNameProducto(producto.getNameProducto());
        productoDTO.setPrecio(producto.getPrecio());
        productoDTO.setLote(producto.getLote());
        productoDTO.setCantidad(producto.getCantidad());
        productoDTO.setFechaVencimiento(producto.getFechavencimiento());

        return productoDTO;
    }
}
