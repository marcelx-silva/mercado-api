package com.newgo.mercadoapi.domain.mappers;


import java.util.Optional;

public interface ObjectDTOMapper<Object,DTO>{
    DTO toDTO(Object object);
}
