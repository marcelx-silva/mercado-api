package com.newgo.mercadoapi.service.privilege;

import com.newgo.mercadoapi.domain.model.Privilege;
import com.newgo.mercadoapi.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrivilegeServiceH2 implements PrivilegeService{
    @Autowired
    PrivilegeRepository privilegeRepository;

    @Override
    public Privilege save(Privilege privilege) {
        return privilegeRepository.save(privilege);
    }

    @Override
    public Optional<Privilege> findByName(String name) {
        return privilegeRepository.findPrivilegeByName(name);
    }
}
