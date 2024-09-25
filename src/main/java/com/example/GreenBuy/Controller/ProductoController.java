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
import java.util.stream.Collectors;

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

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> findAll() {
        List<Producto> productos = productoService.findAll();
        List<ProductoDTO> productoDTOs = productos.stream()
                .map(this::convertToDTO)
                .toList(); // Asegúrate de que estás usando Java 11 o superior para esta sintaxis
        return ResponseEntity.ok(productoDTOs);
    }


    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> createProducto(@RequestBody Map<String, Object> json) {
        Map<String, Object> response = new HashMap<>();
        try {
            ProductoDTO productoDTO = new ProductoDTO();

            // Extraer datos directamente del Map
            productoDTO.setNameProducto((String) json.get("name_producto"));
            productoDTO.setPrecio((Integer) json.get("precio"));
            productoDTO.setImagen((String) json.get("imagen"));

            // Convertir fecha de String a LocalDate
            String fechaVencimientoStr = (String) json.get("fecha_vencimiento");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fechaVencimiento = LocalDate.parse(fechaVencimientoStr, formatter);
            productoDTO.setFechaVencimiento(fechaVencimiento);

            // Configurar fecha de creación y actualización
            productoDTO.setCreatedAt(LocalDateTime.now());
            productoDTO.setUpdatedAt(LocalDateTime.now());

            // Convertir DTO a entidad
            Producto producto = convertToEntity(productoDTO);
            productoService.save(producto);

            response.put("message", "Producto creado exitosamente");
            response.put("producto", productoDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DateTimeParseException e) {
            response.put("message", "Error de formato en la fecha: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Error procesando la solicitud: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateProducto(@PathVariable Long id, @RequestBody Map<String, Object> json) {
        Map<String, Object> response = new HashMap<>();
        try {
            Producto producto = productoService.getById(id);
            if (producto == null) {
                return ResponseEntity.notFound().build();
            }

            ProductoDTO productoDTO = new ProductoDTO();
            productoDTO.setNameProducto((String) json.get("name_producto"));
            productoDTO.setPrecio((Integer) json.get("precio"));
            productoDTO.setImagen((String) json.get("imagen"));

            // Convertir fecha de String a LocalDate
            String fechaVencimientoStr = (String) json.get("fecha_vencimiento");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fechaVencimiento = LocalDate.parse(fechaVencimientoStr, formatter);
            productoDTO.setFechaVencimiento(fechaVencimiento);

            // Actualizar fechas de creación y modificación
            productoDTO.setCreatedAt(producto.getCreatedAt()); // Mantener la fecha de creación original
            productoDTO.setUpdatedAt(LocalDateTime.now()); // Actualizar la fecha de modificación

            // Convertir DTO a entidad y actualizar
            producto = convertToEntity(productoDTO);
            productoService.save(producto);

            response.put("message", "Producto actualizado exitosamente");
            response.put("producto", productoDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DateTimeParseException e) {
            response.put("message", "Error de formato en la fecha: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Error procesando la solicitud: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        Producto producto = productoService.getById(id);
        if (producto != null) {
            productoService.delete(producto);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Métodos de conversión de DTO a entidad y viceversa
    private Producto convertToEntity(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setNameProducto(productoDTO.getNameProducto());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setImagen(productoDTO.getImagen());
        producto.setCreatedAt(productoDTO.getCreatedAt());
        producto.setUpdatedAt(productoDTO.getUpdatedAt());
        // Puedes agregar otras propiedades como la categoría o lotes si lo deseas
        return producto;
    }

    private ProductoDTO convertToDTO(Producto producto) {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setNameProducto(producto.getNameProducto());
        productoDTO.setPrecio(producto.getPrecio());
        productoDTO.setImagen(producto.getImagen());
        productoDTO.setCreatedAt(producto.getCreatedAt());
        productoDTO.setUpdatedAt(producto.getUpdatedAt());
        // Asegúrate de incluir la fecha de vencimiento aquí si corresponde
        return productoDTO;
    }
}
