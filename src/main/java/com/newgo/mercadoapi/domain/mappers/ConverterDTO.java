package com.newgo.mercadoapi.domain.mappers;


import java.util.Optional;

public interface ConverterDTO<Object,DTO>{
    DTO toDTO(Object object);
}
