package com.newgo.mercadoapi.config;

import com.newgo.mercadoapi.domain.model.Privilege;
import com.newgo.mercadoapi.domain.model.Role;
import com.newgo.mercadoapi.domain.model.User;
import com.newgo.mercadoapi.enums.PrivilegesType;
import com.newgo.mercadoapi.enums.RolesType;
import com.newgo.mercadoapi.repository.UserRepository;
import com.newgo.mercadoapi.service.privilege.PrivilegeService;
import com.newgo.mercadoapi.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class SetupDataLoaderUser implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;
    @Autowired
    private RoleService roleService;

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;

        final Optional<Privilege> readActivedProduct = createPrivilegeIfNotFound(PrivilegesType.READ_ACTIVED_PRODUCT_PRIVILEGE.toString());
        final Optional<Privilege> readProduct = createPrivilegeIfNotFound(PrivilegesType.READ_PRODUCT_PRIVILEGE.toString());
        final Optional<Privilege> writeProduct = createPrivilegeIfNotFound(PrivilegesType.WRITE_PRODUCT.toString());
        final Optional<Privilege> writeShoppingList = createPrivilegeIfNotFound(PrivilegesType.WRITE_SHOPPING_LIST.toString());

        final Set<Privilege> admPrivileges = new HashSet<>();
        admPrivileges.add(readProduct.get());
        admPrivileges.add(writeProduct.get());
        admPrivileges.add(readActivedProduct.get());

        final Set<Privilege> customerPrivileges = new HashSet<>();
        customerPrivileges.add(writeShoppingList.get());
        customerPrivileges.add(readActivedProduct.get());

        final Optional<Role> admRole = createRoleIfNotFound(RolesType.ROLE_ADMINISTRATOR.toString(),admPrivileges);
        final Optional<Role> customerRole = createRoleIfNotFound(RolesType.ROLE_CUSTOMER.toString(),customerPrivileges);


        createUserIfNotFound(customerRole.get());
        createAdmIfNotFound(admRole.get());

        alreadySetup = true;

    }


    @Transactional
    Optional<Role> createRoleIfNotFound(final String name, final Set<Privilege> privileges){
        Optional<Role> role = roleService.findByName(name);
        if (role.isPresent())
            return role;

        role = Optional.of(new Role(name,privileges));
        role = Optional.of(roleService.save(role.get()));
        return role;
    }

    @Transactional
    Optional<Privilege> createPrivilegeIfNotFound(final String name){
        Optional<Privilege> privilege = privilegeService.findByName(name);
        if (privilege.isPresent())
            return privilege;

        privilege = Optional.of(new Privilege(name));
        privilege = Optional.of(privilegeService.save(privilege.get()));

        return privilege;
    }

    @Transactional
    void createUserIfNotFound(Role role){
        Optional<User> userOptional = userRepository.findUserByEmail("customerTest@gmail.com");
        if (userOptional.isPresent())
            return;

        User customer = new User();
        customer.setUsername("testCustomer");
        customer.setEmail("customerTest@gmail.com");
        customer.setPassword(bCryptPasswordEncoder.encode("senhaTestCustomer"));
        customer.setRoles(role);
        userRepository.save(customer);
    }

    @Transactional
    void createAdmIfNotFound(Role role){
        Optional<User> userOptional = userRepository.findUserByEmail("admTest@gmail.com");
        if (userOptional.isPresent())
            return;

        User administrator = new User();
        administrator.setUsername("testAdm");
        administrator.setPassword(bCryptPasswordEncoder.encode("senhaTestAdm"));
        administrator.setEmail("admTest@gmail.com");
        administrator.setRoles(role);
        userRepository.save(administrator);
    }
}
