package com.newgo.mercadoapi.repository;

import com.newgo.mercadoapi.domain.model.ShoppingList;
import com.newgo.mercadoapi.domain.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;
import java.util.Set;

@Repository
public interface ShoppingListRepository extends CrudRepository<ShoppingList, UUID> {
    Set<ShoppingList> findAllByUser(User user);
    ShoppingList findShoppingListByNameAndUser(String listName, User user);
    Optional<ShoppingList> findShoppingListByName(String listName);
}
