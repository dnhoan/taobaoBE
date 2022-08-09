package com.example.taobao_be.Services;

import com.example.taobao_be.DTO.CategoryDTO;
import com.example.taobao_be.DTO.PageDTO;
import com.example.taobao_be.models.Category;
import com.example.taobao_be.repositories.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    public PageDTO<CategoryDTO> getAll(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Category> page = this.categoryRepository.findAll(pageable);
        List<CategoryDTO> categoryDTOList = page.stream().map(u -> this.modelMapper.map(u, CategoryDTO.class)).collect(Collectors.toList());
        return new PageDTO<CategoryDTO>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                categoryDTOList,
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
    }

    public List<CategoryDTO> searchCategoriesByName(String name) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Category> page = this.categoryRepository.findCategoryByNameIsContaining(name, pageable);
        List<CategoryDTO> categoryDTOList = page.stream().map(u -> this.modelMapper.map(u, CategoryDTO.class)).collect(Collectors.toList());
        return categoryDTOList;
    }

    public CategoryDTO create(Category category) throws Exception {
        try {
            this.categoryRepository.saveAndFlush(category);
            return this.modelMapper.map(category, CategoryDTO.class);
        } catch (Exception exception) {
            throw new Exception("Create User false");
        }
    }

    public CategoryDTO update(Category category) {
        Category u = this.categoryRepository.save(category);
        return this.modelMapper.map(u, CategoryDTO.class);
    }

    public CategoryDTO delete(Category category) {
        category.setStatus(0);
        this.categoryRepository.save(category);
        return this.modelMapper.map(category, CategoryDTO.class);
    }

    public CategoryDTO getById(Long id) throws Exception {
        Optional<Category> category = this.categoryRepository.findById(id);
        if (category.isPresent()) {
            return this.modelMapper.map(category.get(), CategoryDTO.class);
        }
        throw new Exception("Not found category by id");
    }

    public Category getCategoryById(Long id) throws Exception {
        Optional<Category> category = this.categoryRepository.findById(id);
        if (category.isPresent()) {
            return category.get();
        }
        throw new Exception("Not found category by id");
    }

}
