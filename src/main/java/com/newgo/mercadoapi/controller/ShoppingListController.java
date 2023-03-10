package com.newgo.mercadoapi.controller;

import com.newgo.mercadoapi.service.shoppinglistproduct.ShoppingListProductServiceH2;
import com.newgo.mercadoapi.domain.dto.product.ProductAddListDTO;
import com.newgo.mercadoapi.domain.dto.shoppinglist.ShoppingListCreateDTO;
import com.newgo.mercadoapi.domain.dto.shoppinglist.ShoppingListRequestDTO;
import com.newgo.mercadoapi.service.shoppinglist.ShoppingListService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping("/shop-lists")
public class ShoppingListController {
    @Autowired
    ShoppingListService shoppingListService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ShoppingListProductServiceH2 shoppingListProductServiceH2;

    @PostMapping("/managed-lists")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMINISTRATOR','ROLE_CUSTOMER')")
    public ResponseEntity<Object> saveList(Principal principal,@RequestBody ShoppingListCreateDTO shoppingListCreateDTO) {
        shoppingListService.save(principal.getName(), shoppingListCreateDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(shoppingListService
                        .findByName(principal.getName(), shoppingListCreateDTO.getName()));
    }


    @GetMapping("/managed-lists/list")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMINISTRATOR','ROLE_CUSTOMER')")
    public ResponseEntity<Object> getUniqueShoppingList(Principal principal, @RequestParam("name") String listName) {
        ShoppingListRequestDTO shoppingListRequestDTO =
                modelMapper
                        .map(shoppingListService
                                .findByName(principal.getName(), listName), ShoppingListRequestDTO.class);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(shoppingListRequestDTO);
    }

    @GetMapping("/managed-lists/all")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMINISTRATOR','ROLE_CUSTOMER')")
    public ResponseEntity<Object> getAllShoppingList(Principal principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(shoppingListService.findAllByUser(principal.getName()));
    }

    @GetMapping("/managed-lists/list/{id}/price")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMINISTRATOR','ROLE_CUSTOMER')")
    public ResponseEntity<Object> getListTotalPrice(Principal principal, @PathVariable("id")UUID listId){
        Double listPrice = shoppingListService.queryListPrice(principal.getName(), listId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("List Price: "+listPrice);
    }

    @DeleteMapping("/managed-list/list/{name}")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMINISTRATOR','ROLE_CUSTOMER')")
    public ResponseEntity<Object> deleteShoppingList(Principal principal, @RequestParam("name") String listName) {
        shoppingListService.deleteByName(principal.getName(), listName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("List Deleted");
    }
    @PostMapping("/managed-lists/list/product")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMINISTRATOR','ROLE_CUSTOMER')")
    public ResponseEntity<Object> addProduct(Principal principal,
                                             @RequestParam("name") String listName,
                                             @RequestBody ProductAddListDTO productAddListDTO){

        productAddListDTO.setListName(listName);
        productAddListDTO.setUser(principal.getName());
        shoppingListProductServiceH2.addProductToList(productAddListDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Produto Adicionado");
    }

    @DeleteMapping ("/managed-lists/list/product")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMINISTRATOR','ROLE_CUSTOMER')")
    public ResponseEntity<Object> removeProduct(Principal principal,
                                                @RequestParam("name") String listName,
                                                @RequestParam("product") String productName){


        ProductAddListDTO productAddListDTO =
                new ProductAddListDTO(productName,null,listName,principal.getName());

        shoppingListProductServiceH2.removeProductFromList(productAddListDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Produto Removido da Lista");
    }

    @PutMapping("/managed-lists/list/product/quantity")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMINISTRATOR','ROLE_CUSTOMER')")
    public ResponseEntity<Object> changeQuantity(Principal principal,
                                                 @RequestParam("name") String listName,
                                                 @RequestParam("product") String productName,
                                                 @RequestParam("quantity") Integer quantity){

        ProductAddListDTO productAddListDTO =
                new ProductAddListDTO(productName,quantity,listName,principal.getName());

        shoppingListProductServiceH2.changeProductQuantity(productAddListDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Quantidade Atualizada");
    }
}
