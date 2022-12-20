package com.newgo.mercadoapi.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "products_shopping_list")
public class ShoppingListProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    @ManyToOne
    private ShoppingList shoppingList;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product products;
    @Column(name = "quantity_product",nullable = false)
    private Integer amountProductAdded;

}
