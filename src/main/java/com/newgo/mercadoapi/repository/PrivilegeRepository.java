package com.newgo.mercadoapi.repository;

import com.newgo.mercadoapi.domain.model.Privilege;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PrivilegeRepository extends CrudRepository<Privilege, UUID> {
    Optional<Privilege> findPrivilegeByName(String name);
}

