package com.newgo.mercadoapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {
    private String name;
    private String description;
    private String imageProductURL;
    private Integer quantity;
    private Boolean status;
}
