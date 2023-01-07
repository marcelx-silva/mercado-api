package com.newgo.mercadoapi.domain.dto.shoppinglist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShoppingListCreateDTO {
    private String name;
    private String description;
}

