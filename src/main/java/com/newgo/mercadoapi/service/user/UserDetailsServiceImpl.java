package com.newgo.mercadoapi.service.user;

import com.newgo.mercadoapi.domain.dto.LoginDTO;
import com.newgo.mercadoapi.domain.model.User;
import com.newgo.mercadoapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        return userOptional.orElse(null);
    }

    public void verifyUserPassword(LoginDTO loginDTO){
        System.out.println("DESISTA3");
        UserDetails userDetails = loadUserByUsername(loginDTO.getUsername());
        boolean passwordCheck = bCryptPasswordEncoder.matches(loginDTO.getPassword(), userDetails.getPassword());
        if (!passwordCheck)
            throw new RuntimeException("Usuário não encontrado");
    }
}
