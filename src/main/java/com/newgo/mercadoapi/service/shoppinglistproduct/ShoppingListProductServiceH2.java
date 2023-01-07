package com.newgo.mercadoapi.service.shoppinglistproduct;

import com.newgo.mercadoapi.domain.dto.product.ProductAddListDTO;
import com.newgo.mercadoapi.domain.dto.product.ProductListDTO;
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
                                product.getAmountProductAdded(), product.getProductPrice())));
        return productDTOs;
    }

    @Transactional
    public void addProductToList(ProductAddListDTO productAddListDTO) {
        Optional<User> userOptional = userRepository.findUserByUsername(productAddListDTO.getUser());
        Optional<Product> productOptional = productRepository.findProductByName(productAddListDTO.getName());

        isUserAndProductEmpty(userOptional, productOptional);

        setQuantity(productOptional.get(), productAddListDTO.getAmount());

        Double totalPrice = getProductTotalPriceInList(productOptional.get(), productAddListDTO.getAmount());

        ShoppingList shoppingList =
                getListFromUser(productAddListDTO.getListName(), userOptional.get());

        ShoppingListProduct listProduct =
                new ShoppingListProduct(shoppingList,
                        productOptional.get(),
                        productAddListDTO.getAmount(),
                        totalPrice);

        shoppingListProductRepository.save(listProduct);
    }

    @Transactional
    public void removeProductFromList(ProductAddListDTO productAddListDTO) {
        Optional<User> userOptional = userRepository.findUserByUsername(productAddListDTO.getUser());
        Optional<Product> productOptional = productRepository.findProductByName(productAddListDTO.getName());

        isUserAndProductEmpty(userOptional, productOptional);

        ShoppingList shoppingList =
                getListFromUser(productAddListDTO.getListName(), userOptional.get());

        shoppingListProductRepository
                .removeProductFromList(productOptional.get().getUuid(),
                        shoppingList.getUuid());
    }

    @Transactional
    public void changeProductQuantity(ProductAddListDTO productAddListDTO) {
        Optional<User> userOptional = userRepository.findUserByUsername(productAddListDTO.getUser());
        Optional<Product> productOptional = productRepository.findProductByName(productAddListDTO.getName());
        isUserAndProductEmpty(userOptional, productOptional);

        ShoppingList shoppingList =
                getListFromUser(productAddListDTO.getListName(), userOptional.get());

        shoppingListProductRepository
                .changeProductQuantityFromList(productOptional.get().getUuid(),
                        shoppingList.getUuid(),
                        productAddListDTO.getAmount());


        setQuantity(productOptional.get(), productAddListDTO.getAmount());
    }

    @Transactional
    void setQuantity(Product product, Integer amountToBeAdded) {
        int newQuantity = product.getQuantity() - amountToBeAdded;

        if (newQuantity < 0)
            throw new RuntimeException();

        if (amountToBeAdded == 0)
            throw new RuntimeException();

        productRepository.setProductNewQuantity(newQuantity, product.getName());
    }

    private void isUserAndProductEmpty(Optional<User> userOptional, Optional<Product> productOptional) {
        if (userOptional.isEmpty() || productOptional.isEmpty())
            throw new RuntimeException();
    }

    private ShoppingList getListFromUser(String listName, User user) {
        Optional<ShoppingList> shoppingList = Optional.ofNullable(shoppingListRepository.findShoppingListByNameAndUser(listName, user));
        if (shoppingList.isEmpty())
            throw new RuntimeException();
        return shoppingList.get();
    }

    private Double getProductTotalPriceInList(Product product, Integer amount) {
        return product.getPrice() * amount;
    }
}
