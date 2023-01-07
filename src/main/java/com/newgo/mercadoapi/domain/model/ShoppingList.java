package com.newgo.mercadoapi.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "shoppingList")
@NoArgsConstructor
public class ShoppingList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    @Column(name = "list_name",nullable = false,length = 50)
    private String name;
    @Column(name = "list_description",nullable = true,length = 100)
    String description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "total_price",columnDefinition="Decimal(10,2) default '0.0'", updatable = true)
    private Double listTotalPrice = 0.0;

    public ShoppingList(String name, String description, User user) {
        this.name = name;
        this.description = description;
        this.user = user;
    }
}
