package com.newgo.mercadoapi.repository;

import com.newgo.mercadoapi.domain.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ProductRepository extends CrudRepository<Product, UUID> {
    Optional<Product> findProductByName(String name);
    void deleteProductByName(String name);
    Set<Product> findAllByStatusIsTrue();
}
