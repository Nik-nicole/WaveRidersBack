package com.example.GreenBuy.Controller;



import com.example.GreenBuy.Entities.Lote;
import com.example.GreenBuy.Services.LoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lotes")
public class LoteController {

    @Autowired
    private LoteService loteService;

    @GetMapping("/{id}")
    public ResponseEntity<Lote> getById(@PathVariable Long id) {
        Lote lote = loteService.getById(id);
        return lote != null ? ResponseEntity.ok(lote) : ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Lote>> findAll() {
        List<Lote> lotes = loteService.findAll();
        return ResponseEntity.ok(lotes);
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> createLote(@RequestBody Lote lote) {
        Map<String, Object> response = new HashMap<>();
        try {
            loteService.save(lote);
            response.put("message", "Lote creado exitosamente");
            response.put("lote", lote);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("message", "Error procesando la solicitud: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateLote(@PathVariable Long id, @RequestBody Lote loteDetails) {
        Map<String, Object> response = new HashMap<>();
        try {
            Lote lote = loteService.getById(id);
            if (lote == null) {
                return ResponseEntity.notFound().build();
            }

            // Actualiza los campos del lote existente

            // Agrega aquí los demás campos que necesites actualizar

            loteService.save(lote);
            response.put("message", "Lote actualizado exitosamente");
            response.put("lote", lote);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error procesando la solicitud: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLote(@PathVariable Long id) {
        Lote lote = loteService.getById(id);
        if (lote != null) {
            loteService.delete(lote);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
