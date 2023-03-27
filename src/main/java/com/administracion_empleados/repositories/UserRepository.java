package com.administracion_empleados.repositories;

import com.administracion_empleados.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query("SELECT u FROM User u WHERE u.email = :email") 
    public User findUserByEmail(@Param("email") String email);
}
