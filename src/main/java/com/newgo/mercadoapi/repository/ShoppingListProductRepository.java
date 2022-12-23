package com.newgo.mercadoapi.repository;

import com.newgo.mercadoapi.domain.model.Product;
import com.newgo.mercadoapi.domain.model.ShoppingList;
import com.newgo.mercadoapi.domain.model.ShoppingListProduct;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ShoppingListProductRepository extends CrudRepository<ShoppingListProduct, UUID> {
    List<ShoppingListProduct> findAllByShoppingList(ShoppingList shoppingList);

    @Modifying
    @Query(value =  "DELETE " +
            "ShoppingListProduct pl " +
            "WHERE " +
            "pl.products.uuid =:product AND " +
            "pl.shoppingList.uuid =:list")
    void removeProductFromList(@Param("product") UUID productId, @Param("list") UUID productListId);

    @Modifying
    @Query(value = "UPDATE " +
            "ShoppingListProduct pl " +
            "SET " +
            "pl.amountProductAdded =:quantity " +
            "WHERE " +
            "pl.products.uuid =:product AND " +
            "pl.shoppingList.uuid =:list")
    void changeProductQuantityFromList(@Param("product") UUID productId,
                                       @Param("list") UUID productListId,
                                       @Param("quantity") int quantity);

}
