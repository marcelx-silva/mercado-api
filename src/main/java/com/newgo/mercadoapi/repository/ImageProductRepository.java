package com.newgo.mercadoapi.repository;

import com.newgo.mercadoapi.domain.model.ImageProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ImageProductRepository extends CrudRepository<ImageProduct, UUID> {
    Optional<ImageProduct> findImageProductByName(String name);
    void deleteImageProductByName(String name);
    ImageProduct findImageProductByUrl(String URL);
}
