package com.newgo.mercadoapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "products_shopping_list")
@NoArgsConstructor
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
    @Column(name = "product_price",nullable = false)
    private Double productPrice;

    public ShoppingListProduct(ShoppingList shoppingList, Product products, Integer amountProductAdded,Double productPrice) {
        this.shoppingList = shoppingList;
        this.products = products;
        this.amountProductAdded = amountProductAdded;
        this.productPrice = productPrice;
    }
}
