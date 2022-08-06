package com.example.taobao_be.repositories;

import com.example.taobao_be.DTO.PageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface IService<T, K> {

    PageDTO<T> getAll(int size, int limit);

    T create(T t);

    T update(T t);

    boolean delete(K k);

    T getById(K k);
}
