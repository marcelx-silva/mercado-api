package com.newgo.mercadoapi.controller;

import com.newgo.mercadoapi.config.security.utils.JwtTokenUtil;
import com.newgo.mercadoapi.domain.dto.auth.AuthResponseDTO;
import com.newgo.mercadoapi.domain.dto.auth.LoginDTO;
import com.newgo.mercadoapi.service.user.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO loginDTO) {
        userDetailsServiceImpl.verifyUserPassword(loginDTO);
        String token = jwtTokenUtil.generateAccessToken(loginDTO.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(new AuthResponseDTO(token));
    }
}
