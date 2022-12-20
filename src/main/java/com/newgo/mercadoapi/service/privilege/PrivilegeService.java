package com.newgo.mercadoapi.service.privilege;

import com.newgo.mercadoapi.domain.model.Privilege;

import java.util.Optional;

public interface PrivilegeService {
    Privilege save(Privilege privilege);
    Optional<Privilege> findByName(String name);
}
