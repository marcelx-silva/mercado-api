package com.newgo.mercadoapi.domain.model;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Entity
@Table(name = "privileges")
public class Privilege implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    @Column(name = "name",unique = true,nullable = false)
    private String name;
    @ManyToMany(mappedBy = "privileges")
    private Set<Role> role;
    public Privilege(String name){
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return this.name;
    }
}
