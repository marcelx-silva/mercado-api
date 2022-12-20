package com.newgo.mercadoapi.repository;

import com.newgo.mercadoapi.domain.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends CrudRepository<Role, UUID> {
    Optional<Role> findRoleByName(String name);
}
