package com.newgo.mercadoapi.service.shoppinglist;

import com.newgo.mercadoapi.domain.dto.shoppinglist.ShoppingListCreateDTO;
import com.newgo.mercadoapi.domain.dto.shoppinglist.ShoppingListRequestDTO;

import java.util.Set;

public interface ShoppingListService {
    void save(String user, ShoppingListCreateDTO shoppingListCreateDTO);
    Set<ShoppingListRequestDTO> findAllByUser(String user);
    ShoppingListRequestDTO findByName(String user, String listName);
    void deleteByName(String user, String listName);


}
