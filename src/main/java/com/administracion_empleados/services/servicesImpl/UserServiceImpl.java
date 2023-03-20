package com.administracion_empleados.services.servicesImpl;

import java.util.List;

import com.administracion_empleados.models.User;
import com.administracion_empleados.repositories.UserRepository;
import com.administracion_empleados.services.UserService;

public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    @Override
    public void save(User user) {
        userRepository.save(user);
    }
    
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    
}
