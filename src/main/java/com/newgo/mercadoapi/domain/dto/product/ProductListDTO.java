package com.newgo.mercadoapi.domain.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductListDTO {
    private String name;
    private String description;
    private Integer quantity;
}
