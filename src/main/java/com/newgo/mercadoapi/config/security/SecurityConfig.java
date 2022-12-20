package com.newgo.mercadoapi.config.security;

import com.newgo.mercadoapi.config.security.utils.JwtFilter;
import com.newgo.mercadoapi.config.security.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable();
        httpSecurity.authorizeRequests()
                .antMatchers("/authorize/user/login","*/user/*").hasAnyAuthority("ROLE_CUSTOMER", "ROLE_ADMINISTRATOR")
                .antMatchers("*/adm/*").hasAuthority("ROLE_ADMINISTRATOR")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    public OncePerRequestFilter jwtFilter() {
        return new JwtFilter(jwtTokenUtil,userDetailsService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(this.bCryptPasswordEncoder);
    }


}
