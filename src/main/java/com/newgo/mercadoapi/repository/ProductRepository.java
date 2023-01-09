package com.newgo.mercadoapi.repository;

import com.newgo.mercadoapi.domain.dto.product.ProductDTO;
import com.newgo.mercadoapi.domain.model.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ProductRepository extends CrudRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    Optional<Product> findProductByName(String name);
    void deleteProductByName(String name);
    Set<Product> findAllByStatusIsTrue();
    @Modifying
    @Query(value =
            "UPDATE Product p " +
                    "SET " +
                    "p.name = :#{#productDTO.name}, " +
                    "p.description = :#{#productDTO.description}, " +
                    "p.quantity = :#{#productDTO.quantity},"+
                    "p.status = :#{#productDTO.status} " +
                    "WHERE p.uuid =:id")
    void updateProduct(@Param("id") UUID uuid, @Param("productDTO") ProductDTO productDTO);

    @Modifying
    @Query(value = "UPDATE Product p SET p.quantity =:newQuantity WHERE p.name =:name")
    void setProductNewQuantity(int newQuantity, String name);

    @Modifying
    @Query(value = "UPDATE Product p SET p.status =:newStatus WHERE p.uuid =:id")
    void setProductStatus(@Param("id") UUID uuid, @Param("newStatus") boolean newStatus);

    Set<Product> findByPriceBetween(Double min, Double max);
}
