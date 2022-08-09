package com.example.taobao_be.repositories;

import com.example.taobao_be.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findCategoryByNameIsContaining(String name, Pageable pageable);
}
