package com.newgo.mercadoapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductAddListDTO {
    private String name;
    private Integer amount;
    private String listName;
}
