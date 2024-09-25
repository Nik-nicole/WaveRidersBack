package com.example.GreenBuy.Services;


import com.example.GreenBuy.Entities.Lote;
import com.example.GreenBuy.Repository.LoteRepository;
import com.example.GreenBuy.Services.dao.Idao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoteService implements Idao<Lote, Long> {

    @Autowired
    private LoteRepository loteRepository;

    @Override
    public List<Lote> findAll() {
        return this.loteRepository.findAll();
    }

    @Override
    public Lote getById(Long id) {
        return this.loteRepository.getReferenceById(id);
    }

    @Override
    public void save(Lote lote) {
        this.loteRepository.save(lote);
    }

    @Override
    public void delete(Lote lote) {
        this.loteRepository.delete(lote);
    }


}
