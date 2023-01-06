package com.newgo.mercadoapi.domain.dto.shoppinglist;

import com.newgo.mercadoapi.domain.dto.product.ProductListDTO;
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
    private Set<ProductListDTO> products;
}
