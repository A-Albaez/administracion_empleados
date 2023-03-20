package com.administracion_empleados.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.administracion_empleados.services.UserService;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersController {

    @Autowired
    private UserService uService;
    
    @GetMapping("/users")
    public String allUsers(Model model) {
        model.addAttribute("employees", uService.findAll());
        return "pages/table-employee";
    }
    
    @GetMapping("/users/add")
    public String addEmployees(Model model){
        return "pages/register";
    }

    @PostMapping("/users/add")
    public String save(@ModelAttribute UserService form, Model model) {
        return "redirect:/users";
    }
    
}
