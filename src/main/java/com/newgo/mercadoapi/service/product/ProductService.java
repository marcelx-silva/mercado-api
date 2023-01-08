package com.newgo.mercadoapi.service.product;

import com.newgo.mercadoapi.domain.dto.product.ProductDTO;
import com.newgo.mercadoapi.domain.model.Product;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ProductService {
    void save(ProductDTO productDTO);
    Set<ProductDTO> findAll();
    Optional<ProductDTO> findById(UUID uuid);
    Optional<ProductDTO> findByName(String name);
    void deleteById(UUID uuid);
    void deleteByName(String name);
    Set<ProductDTO> findAllByAtivadoIsTrue();
    void updateProduct(UUID uuid,ProductDTO productDTO);
    void updateProductStatus(UUID uuid);
    Set<ProductDTO> findProductsBetween(Double min, Double max);
}
