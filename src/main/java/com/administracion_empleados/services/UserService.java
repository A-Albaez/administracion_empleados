package com.administracion_empleados.services;

import java.util.List;

import com.administracion_empleados.models.User;

public interface UserService{
    List<User> findAll();
    User findById(Long id);
    void save(User user);
    void deleteById(Long id);
    User findUserByEmail(String email);
}
