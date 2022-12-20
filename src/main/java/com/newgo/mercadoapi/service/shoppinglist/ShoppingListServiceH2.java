package com.newgo.mercadoapi.service.shoppinglist;


import com.newgo.mercadoapi.domain.dto.ShoppingListCreateDTO;
import com.newgo.mercadoapi.domain.dto.ShoppingListRequestDTO;
import com.newgo.mercadoapi.domain.model.ShoppingList;
import com.newgo.mercadoapi.domain.model.User;
import com.newgo.mercadoapi.repository.ShoppingListRepository;
import com.newgo.mercadoapi.repository.UserRepository;
import com.newgo.mercadoapi.service.shoppinglistproduct.ShoppingListProductServiceH2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ShoppingListServiceH2 implements ShoppingListService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShoppingListRepository shoppingListRepository;
    @Autowired
    private ShoppingListProductServiceH2 listProduct;


    @Override
    @Transactional
    public void save(String user, ShoppingListCreateDTO shoppingListCreateDTO) {
        Optional<User> userOptional = userRepository.findUserByUsername(user);
        if (userOptional.isEmpty())
            return;

        ShoppingList shoppingList = modelMapper.map(shoppingListCreateDTO, ShoppingList.class);
        shoppingList.setUser(userOptional.get());
        shoppingListRepository.save(shoppingList);
    }

    @Override
    public Set<ShoppingListRequestDTO> findAllByUser(String user) {
        Optional<User> userOptional = userRepository.findUserByUsername(user);
        if (userOptional.isEmpty())
            return null;

        Set<ShoppingListRequestDTO> shoppingListRequestDTOs = new HashSet<>();
        shoppingListRepository.findAllByUser(userOptional.get()).
                forEach(shoppingList -> shoppingListRequestDTOs
                        .add(modelMapper.map(shoppingList, ShoppingListRequestDTO.class)));
        shoppingListRequestDTOs
                .stream()
                .forEach(each -> each.setProductDTOSet(listProduct.findAllProductsFromShoppingList(each.getName())));

        return shoppingListRequestDTOs;
    }

    @Override
    public ShoppingListRequestDTO findByName(String user, String listName) {
        Optional<User> userOptional = userRepository.findUserByUsername(user);
        if (userOptional.isEmpty())
            return null;

        ShoppingListRequestDTO shoppingListRequestDTO =
                modelMapper.map(shoppingListRepository.findShoppingListByNameAndUser(listName, userOptional.get()), ShoppingListRequestDTO.class);

        shoppingListRequestDTO
                .setProductDTOSet(listProduct.findAllProductsFromShoppingList(listName));
        return shoppingListRequestDTO;
    }

    @Override
    @Transactional
    public void deleteByName(String user, String listName) {
        Optional<User> userOptional = userRepository.findUserByUsername(user);
        if (userOptional.isEmpty())
            return;

        shoppingListRepository.delete(shoppingListRepository.findShoppingListByNameAndUser(listName,userOptional.get()));
    }
}
