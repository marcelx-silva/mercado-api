package com.newgo.mercadoapi.service.shoppinglistproduct;

import com.newgo.mercadoapi.domain.dto.ProductDTO;
import com.newgo.mercadoapi.domain.dto.ProductListDTO;
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

    public Set<ProductListDTO> findAllProductsFromShoppingList(String listName) {
        Optional<ShoppingList> shoppingListOptional = shoppingListRepository.findShoppingListByName(listName);
        if (shoppingListOptional.isEmpty())
            return null;
        Set<ProductListDTO> productDTOs = new HashSet<>();
        shoppingListProductRepository.
                findAllByShoppingList(shoppingListOptional.get())
                .stream()
                .forEach(product -> productDTOs
                        .add(new ProductListDTO(product.getProducts().getName(),
                                product.getProducts().getDescription(),
                                product.getAmountProductAdded())));
        return productDTOs;
    }
}
