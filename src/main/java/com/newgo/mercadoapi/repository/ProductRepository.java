package com.newgo.mercadoapi.repository;

import com.newgo.mercadoapi.domain.dto.product.ProductDTO;
import com.newgo.mercadoapi.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findProductByName(String name);
    void deleteProductByName(String name);
    Page<Product> findAllByStatusIsTrue(Pageable pageable);
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

    Page<Product> findByPriceBetween(Double min, Double max, Pageable pageable);  
    
    @Query(value = "SELECT p, c.name FROM Product p  JOIN Category  c ON(p.category = c.uuid) WHERE CONCAT(p.name,'',p.description,p.category.name) LIKE %:word%")
    Page<Product> searchByKeyWord(@Param("word") String keyword, Pageable pageable);
}
  