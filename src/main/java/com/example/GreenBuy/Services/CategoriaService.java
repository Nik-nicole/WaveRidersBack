package com.example.GreenBuy.Services;


import com.example.GreenBuy.Entities.Categoria;
import com.example.GreenBuy.Repository.CategoriaRepository;
import com.example.GreenBuy.Services.dao.Idao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaService implements Idao<Categoria, Long> {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> findAll() {
        return this.categoriaRepository.findAll();
    }

    @Override
    public Categoria getById(Long id) {
        return this.categoriaRepository.getReferenceById(id);
    }

    @Override
    public void save(Categoria categoria) {
        this.categoriaRepository.save(categoria);
    }

    @Override
    public void delete(Categoria categoria) {
        this.categoriaRepository.delete(categoria);
    }

    // Métodos adicionales específicos para Categoria si son necesarios
}
