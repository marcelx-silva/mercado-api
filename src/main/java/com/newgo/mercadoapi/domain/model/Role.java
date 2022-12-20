package com.newgo.mercadoapi.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name="roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    @Column(name = "name",unique = true,nullable = false)
    private String name;
    @ManyToMany
    @JoinTable(name = "roles_privileges",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "privilege_id")})
    @ToString.Exclude
    private Set<Privilege> privileges;

    public Role(String name, Set<Privilege> privileges) {
        this.name = name;
        this.privileges = privileges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return uuid != null && Objects.equals(uuid, role.uuid);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String getAuthority() {
        return this.name.toString();
    }
}
