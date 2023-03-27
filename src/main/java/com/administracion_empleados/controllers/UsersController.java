package com.administracion_empleados.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.administracion_empleados.models.User;
import com.administracion_empleados.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UsersController {

    @Autowired
    private UserService uService;

    List<String> employee;
    
    public void addCurrentUserToModel(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User currentUser = uService.findUserByEmail(username);
        model.addAttribute("currentUser", currentUser);
    }
    
    @GetMapping("/users")
    public String allUsers(Model model) {
        addCurrentUserToModel(model);
        model.addAttribute("employees", uService.findAll());
        return "pages/table-employee";
    }
        

    @GetMapping("users/profile")
    public String userProfile(Model model) {
        addCurrentUserToModel(model);
        return "pages/users-profile";
    }
    
    @GetMapping("/users/add")
    public String registerEmployee(Model model, User user){
        model.addAttribute("user", new User());
        addCurrentUserToModel(model);
        return "pages/register";
    }

    @PostMapping("/users/add")
    public String saveEmployee(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("message", "UNSUCCESSFUL REGISTRATION!");
            addCurrentUserToModel(model);
            return "pages/register";
        }
        uService.save(user);
        model.addAttribute("message", "SUCCESSFUL REGISTRATION!");
        addCurrentUserToModel(model);
        return "redirect:/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteEmployee(@PathVariable(value = "id") Long id, Model model) {
        User user = uService.findById(id);
        if (user == null) {
            model.addAttribute("message", "EMPLOYEE NOT REMOVED!");
            addCurrentUserToModel(model);
            return "redirect:/users";
        }
        uService.deleteById(id);
        model.addAttribute("message", "EMPLOYEE SUCCESSFULLY REMOVED!");
        addCurrentUserToModel(model);
        return "redirect:/users";
    }

    @GetMapping("/users/update/{id}")
    public String editEmployee(@PathVariable(value = "id") Long id, Model model) {
        User user = uService.findById(id);
        if (user == null) {
            model.addAttribute("message", "EMPLOYEE NOT FOUND!");
            addCurrentUserToModel(model);
            return "redirect:/users";
        }
        model.addAttribute("employee", user);
        addCurrentUserToModel(model);
        return "pages/update";
    }
    

    @PostMapping("users/update/{id}")
    public String updateEmployee(@PathVariable("id") Long id, @ModelAttribute("employee") User employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("message", "UNSUCCESSFUL UDATE!");
            addCurrentUserToModel(model);
            return "pages/update";
        }
        uService.save(employee);
        model.addAttribute("message", "SUCCESSFUL UPDATE!");
        addCurrentUserToModel(model);
        return "redirect:/users";
    }
    
}
