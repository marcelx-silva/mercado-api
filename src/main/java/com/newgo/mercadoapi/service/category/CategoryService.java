package com.newgo.mercadoapi.service.category;

import com.newgo.mercadoapi.domain.dto.category.CategoryDTO;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface CategoryService {
    void save(CategoryDTO categoryDTO);
    Set<CategoryDTO> findAll();
    Optional<CategoryDTO> findById(UUID uuid);
    void deleteById(UUID uuid);
}
