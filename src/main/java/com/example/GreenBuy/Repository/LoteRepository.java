package com.example.GreenBuy.Repository;

import com.example.GreenBuy.Entities.Lote;
import com.example.GreenBuy.Entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoteRepository extends JpaRepository<Lote, Long> {
}
