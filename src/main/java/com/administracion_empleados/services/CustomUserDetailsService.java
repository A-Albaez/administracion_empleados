package com.administracion_empleados.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.administracion_empleados.models.User;
import com.administracion_empleados.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository uRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(UserRepository uRepository) {
        this.uRepository = uRepository;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = uRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with email");
        }

        // Aquí se cifra la contraseña
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(encodedPassword) // Se utiliza la contraseña cifrada
                .roles(user.getRole())
                .build();

        return userDetails;
    }

}
