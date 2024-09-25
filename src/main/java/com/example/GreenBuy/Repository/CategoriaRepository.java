package com.example.GreenBuy.Repository;


import com.example.GreenBuy.Entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
