package com.newgo.mercadoapi.service.category;

import com.newgo.mercadoapi.domain.dto.category.CategoryDTO;
import com.newgo.mercadoapi.domain.mappers.ObjectDTOMapper;
import com.newgo.mercadoapi.domain.model.Category;
import com.newgo.mercadoapi.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class CategoryServiceH2 implements CategoryService{

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ObjectDTOMapper<Category,CategoryDTO> categoryMapper;

    @Override
    public void save(CategoryDTO categoryDTO) {
        categoryRepository.save(categoryMapper.toObject(categoryDTO));
    }

    @Override
    public Set<CategoryDTO> findAll() {
        Set<CategoryDTO> categories = new HashSet<>();
        categoryRepository.findAll()
                .forEach(category -> categories.add(categoryMapper.toDTO(category)));

        return categories;
    }

    @Override
    public Optional<CategoryDTO> findById(UUID uuid) {
        Optional<Category> categoryOptional = categoryRepository.findById(uuid);
        return Optional.ofNullable(categoryMapper.toDTO(categoryOptional.get()));
    }

    @Override
    public void deleteById(UUID uuid) {
        categoryRepository.deleteById(uuid);
    }
}
