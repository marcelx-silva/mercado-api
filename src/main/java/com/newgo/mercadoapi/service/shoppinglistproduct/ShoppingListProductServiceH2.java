package com.newgo.mercadoapi.service.shoppinglistproduct;

import com.newgo.mercadoapi.domain.dto.ProductDTO;
import com.newgo.mercadoapi.domain.model.ShoppingList;
import com.newgo.mercadoapi.repository.ShoppingListProductRepository;
import com.newgo.mercadoapi.repository.ShoppingListRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShoppingListProductServiceH2 {

    @Autowired
    ShoppingListProductRepository shoppingListProductRepository;
    @Autowired
    ShoppingListRepository shoppingListRepository;
    @Autowired
    ModelMapper modelMapper;

    public Set<ProductDTO> findAllProductsFromShoppingList(String listName){
        Optional<ShoppingList> shoppingListOptional = shoppingListRepository.findShoppingListByName(listName);
        if (shoppingListOptional.isEmpty())
            return null;
        Set<ProductDTO> productDTOs = new HashSet<>();
        shoppingListProductRepository.
                findAllByShoppingList(shoppingListOptional.get())
                .stream()
                .forEach(product -> productDTOs.add(modelMapper.map(product.getProducts(), ProductDTO.class)));
    return productDTOs;
    }
}
