package com.girevoy.university.service;

import com.girevoy.university.model.entity.Entity;

import java.util.List;

public interface Service <T extends Entity>{
    T insert(T t);
    T findByID(long id);
    List<T> findAll();
    void update(T t);
    void delete(long id);
}
