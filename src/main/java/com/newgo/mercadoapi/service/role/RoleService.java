package com.newgo.mercadoapi.service.role;

import com.newgo.mercadoapi.domain.model.Role;

import java.util.Optional;

public interface RoleService {
    Role save(Role role);
    Optional<Role> findByName(String name);
}
