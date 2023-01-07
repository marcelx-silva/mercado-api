package com.newgo.mercadoapi.domain.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID uuid;
    @Column(name = "name",nullable = false,unique = true)
    private String name;
    @Column(name = "desc_product",nullable = true,length = 150)
    private String description;
    @Column(name = "status",nullable = false)
    private Boolean status;
    @Column(name = "quantity",nullable = false)
    private Integer quantity;
    @Column(name = "price",nullable = false)
    private Double price;
    @OneToOne
    @JoinColumn(name = "image_id",referencedColumnName = "uuid")
    private ImageProduct imageProduct;

    public Product(String name, String description, Boolean status, Integer quantity,
                   Double price,
                   ImageProduct imageProduct) {

        this.name = name;
        this.description = description;
        this.status = status;
        this.quantity = quantity;
        this.price = price;
        this.imageProduct = imageProduct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return uuid != null && Objects.equals(uuid, product.uuid);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
