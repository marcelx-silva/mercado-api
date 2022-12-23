package com.newgo.mercadoapi.service.shoppinglistproduct;

import com.newgo.mercadoapi.domain.dto.ProductAddListDTO;
import com.newgo.mercadoapi.domain.dto.ProductDTO;
import com.newgo.mercadoapi.domain.dto.ProductListDTO;
import com.newgo.mercadoapi.domain.mappers.ConverterDTO;
import com.newgo.mercadoapi.domain.model.Product;
import com.newgo.mercadoapi.domain.model.ShoppingList;
import com.newgo.mercadoapi.domain.model.ShoppingListProduct;
import com.newgo.mercadoapi.domain.model.User;
import com.newgo.mercadoapi.repository.ProductRepository;
import com.newgo.mercadoapi.repository.ShoppingListProductRepository;
import com.newgo.mercadoapi.repository.ShoppingListRepository;
import com.newgo.mercadoapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ShoppingListProductServiceH2 {

    @Autowired
    ShoppingListProductRepository shoppingListProductRepository;
    @Autowired
    ShoppingListRepository shoppingListRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;

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

    @Transactional
    public void addProduct(String user, ProductAddListDTO productAddListDTO){
        Optional<User> userOptional = userRepository.findUserByUsername(user);
        Optional<Product> productOptional = productRepository.findProductByName(productAddListDTO.getName());

        if (userOptional.isEmpty() || productOptional.isEmpty())
            return;

        changeProductQuantity(productOptional.get()
                , productAddListDTO.getAmount());

        Optional<ShoppingList> shoppingList =
                Optional.ofNullable(shoppingListRepository
                        .findShoppingListByNameAndUser(
                                productAddListDTO.getListName(),
                                userOptional.get()));

        ShoppingListProduct listProduct = new ShoppingListProduct(shoppingList.get(),productOptional.get(),productAddListDTO.getAmount());
        shoppingListProductRepository.save(listProduct);
    }

    @Transactional
    void changeProductQuantity(Product product, Integer amountToBeAdded){
        int newQuantity = product.getQuantity() - amountToBeAdded;

        if (newQuantity<0)
            return;

        productRepository.setProductNewQuantity(newQuantity,product.getName());
    }
}
