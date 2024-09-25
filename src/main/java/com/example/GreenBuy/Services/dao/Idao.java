package com.example.GreenBuy.Services.dao;

import java.util.List;

public interface Idao <T, ID> {

    public List<T> findAll();

    public T getById(ID id);

    public void save (T object);

    public void delete (T object);
}

