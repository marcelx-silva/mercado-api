package com.newgo.mercadoapi.domain.mappers;

import com.newgo.mercadoapi.domain.dto.category.CategoryDTO;
import com.newgo.mercadoapi.domain.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper implements ObjectDTOMapper<Category,CategoryDTO> {

    @Override
    public CategoryDTO toDTO(Category object) {
        if (object==null)
            return null;

        return CategoryDTO.builder()
                .uuid(object.getUuid())
                .name(object.getName())
                .description(object.getDescription())
                .build();
    }

    @Override
    public Category toObject(CategoryDTO dto) {
        if (dto==null)
            return null;

        return new Category(dto.getUuid(),dto.getName(),dto.getDescription());
    }
}
