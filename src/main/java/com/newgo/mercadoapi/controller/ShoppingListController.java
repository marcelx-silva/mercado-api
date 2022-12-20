package com.newgo.mercadoapi.controller;


import com.newgo.mercadoapi.domain.dto.ShoppingListCreateDTO;
import com.newgo.mercadoapi.domain.dto.ShoppingListRequestDTO;
import com.newgo.mercadoapi.service.shoppinglist.ShoppingListService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/list-product")

public class ShoppingListController {
    @Autowired
    ShoppingListService shoppingListService;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<Object> saveList(Principal principal, ShoppingListCreateDTO shoppingListCreateDTO) {
        shoppingListService.save(principal.getName(), shoppingListCreateDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(shoppingListService
                        .findByName(principal.getName(), shoppingListCreateDTO.getName()));
    }


    @GetMapping
    public ResponseEntity<Object> getUniqueShoppingList(Principal principal, @RequestParam("name") String listName) {
        ShoppingListRequestDTO shoppingListRequestDTO =
                modelMapper
                        .map(shoppingListService
                                .findByName(principal.getName(), listName), ShoppingListRequestDTO.class);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(shoppingListRequestDTO);
    }

    @GetMapping
    public ResponseEntity<Object> getAllShoppingList(Principal principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(shoppingListService.findAllByUser(principal.getName()));
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteShoppingList(Principal principal, @RequestParam("name") String listName) {
        shoppingListService.deleteByName(principal.getName(), listName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("List Deleted");
    }
}
