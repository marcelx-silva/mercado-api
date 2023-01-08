package com.newgo.mercadoapi.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    @Column(name = "name",nullable = false,unique = true)
    private String name;
    @Column(name = "description",nullable = true)
    private String description;
}
