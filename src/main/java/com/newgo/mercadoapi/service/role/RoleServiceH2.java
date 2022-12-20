package com.newgo.mercadoapi.service.role;

import com.newgo.mercadoapi.domain.model.Role;
import com.newgo.mercadoapi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceH2 implements RoleService{

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findRoleByName(name);
    }
}
