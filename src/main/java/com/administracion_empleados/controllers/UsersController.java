package com.administracion_empleados.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import com.administracion_empleados.models.User;
import com.administracion_empleados.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
@Controller
public class UsersController {

    @Autowired
    private UserService uService;

    List<String> employees;
    
    @GetMapping("/users")
    public String allUsers(Model model) {
        model.addAttribute("employees", uService.findAll());
        return "pages/table-employee";
    }
    

    @GetMapping("users/profile")
    public String userProfile() {
        return "pages/users-profile";
    }
    
    @GetMapping("/users/add")
    public String registerEmployee(Model model, User user){
        model.addAttribute("user", new User());
        return "pages/register";
    }

    @PostMapping("/users/add")
    public String saveEmployee(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("message", "REGISTRO FALLIDO");
            return "pages/register";
        }
        uService.save(user);
        return "redirect:/users";
    }
    
    
}
