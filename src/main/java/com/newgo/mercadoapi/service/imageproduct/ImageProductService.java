package com.newgo.mercadoapi.service.imageproduct;

import com.newgo.mercadoapi.domain.dto.ImageProductDTO;
import com.newgo.mercadoapi.domain.model.ImageProduct;

import java.util.Optional;

public interface ImageProductService {
    void save(ImageProductDTO imageProductDTO);
    Optional<ImageProduct> findByName(String name);
    void deleteByName(String name);
    ImageProduct findByURL(String URL);
}
