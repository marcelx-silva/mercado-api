package com.newgo.mercadoapi.domain.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {
    private UUID uuid;
    private String name;
    private String description;
    private String imageProductURL;
    private Integer quantity;
    private Boolean status;
    private Double price;
    private String category;
}
