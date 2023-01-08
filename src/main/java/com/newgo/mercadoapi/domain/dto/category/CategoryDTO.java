package com.newgo.mercadoapi.domain.dto.category;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryDTO {
    private UUID uuid;
    private String name;
    private String description;
}
