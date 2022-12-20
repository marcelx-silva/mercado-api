package com.newgo.mercadoapi.repository;

import com.newgo.mercadoapi.domain.model.ShoppingList;
import com.newgo.mercadoapi.domain.model.ShoppingListProduct;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ShoppingListProductRepository extends CrudRepository<ShoppingListProduct, UUID> {
    List<ShoppingListProduct> findAllByShoppingList(ShoppingList shoppingList);
}
