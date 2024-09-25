package com.example.GreenBuy.Services;

import com.example.GreenBuy.DTO.ProductoDTO;
import com.example.GreenBuy.Entities.Producto;
import com.example.GreenBuy.Repository.ProductoRepository;
import com.example.GreenBuy.Services.dao.Idao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService implements Idao<Producto, Long> {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> findAll() {
        return this.productoRepository.findAll();
    }

    @Override
    public Producto getById(Long id) {
        return this.productoRepository.getReferenceById(id);
    }

    @Override
    public void save(Producto producto) {
        this.productoRepository.save(producto);
    }

    @Override
    public void delete(Producto producto) {
        this.productoRepository.delete(producto);
    }



}
