package com.newgo.mercadoapi.service.product;

import com.newgo.mercadoapi.domain.dto.product.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ProductService {
    void save(ProductDTO productDTO);
    Page<ProductDTO> findAll(Pageable pageable);
    Optional<ProductDTO> findById(UUID uuid);
    Optional<ProductDTO> findByName(String name);
    void deleteById(UUID uuid);
    void deleteByName(String name);
    Page<ProductDTO> findAllByAtivadoIsTrue(Pageable pageable);
    void updateProduct(UUID uuid,ProductDTO productDTO);
    void updateProductStatus(UUID uuid);
    Page<ProductDTO> findProductsBetween(Double min, Double max, Pageable pageable);
    Set<ProductDTO> searchByKeyWord(String keyWord);
    void updateProductCategory(String category, UUID productId);
    void updateProductPrice(Double price, UUID productId);
}
