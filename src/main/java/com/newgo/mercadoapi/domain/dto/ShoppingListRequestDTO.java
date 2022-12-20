package com.newgo.mercadoapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShoppingListRequestDTO {
    private String name;
    private String description;
    private Set<ProductListDTO> productDTOSet;
}
