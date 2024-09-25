package com.example.GreenBuy.Controller;

import com.example.GreenBuy.DTO.CategoriaDTO; // Asegúrate de tener este DTO
import com.example.GreenBuy.Entities.Categoria;
import com.example.GreenBuy.Services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/categorias")
public class categoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> getById(@PathVariable Long id) {
        Categoria categoria = categoriaService.getById(id);
        return categoria != null ? ResponseEntity.ok(convertToDTO(categoria)) : ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> createCategoria(@RequestBody Map<String, Object> json) {
        Map<String, Object> response = new HashMap<>();
        try {
            CategoriaDTO categoriaDTO = new CategoriaDTO();

            // Extraer datos directamente del Map
            categoriaDTO.setNameCategoria((String) json.get("name_categoria")); // Asegúrate de que este campo exista en el JSON

            // Convertir DTO a entidad
            Categoria categoria = convertToEntity(categoriaDTO);
            categoriaService.save(categoria);

            response.put("message", "Categoría creada exitosamente");
            response.put("categoria", categoriaDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Error procesando la solicitud: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateCategoria(@PathVariable Long id, @RequestBody Map<String, Object> json) {
        Map<String, Object> response = new HashMap<>();
        try {
            Categoria categoria = categoriaService.getById(id);
            if (categoria == null) {
                return ResponseEntity.notFound().build();
            }

            CategoriaDTO categoriaDTO = new CategoriaDTO();
            categoriaDTO.setNameCategoria((String) json.get("name_categoria")); // Asegúrate de que este campo exista en el JSON

            // Convertir DTO a entidad y actualizar
            categoria = convertToEntity(categoriaDTO);
            categoriaService.save(categoria);

            response.put("message", "Categoría actualizada exitosamente");
            response.put("categoria", categoriaDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Error procesando la solicitud: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        Categoria categoria = categoriaService.getById(id);
        if (categoria != null) {
            categoriaService.delete(categoria);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Métodos de conversión de DTO a entidad y viceversa
    private Categoria convertToEntity(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setNameCategoria(categoriaDTO.getNameCategoria()); // Asegúrate de que este campo exista en la entidad
        // Puedes agregar otras propiedades si lo deseas
        return categoria;
    }

    private CategoriaDTO convertToDTO(Categoria categoria) {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setNameCategoria(categoria.getNameCategoria()); // Asegúrate de que este campo exista en el DTO
        // Asegúrate de incluir otras propiedades si corresponde
        return categoriaDTO;
    }
}
