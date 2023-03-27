package com.administracion_empleados.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class HomeController {
    @Autowired
    private UsersController usersController;

    @GetMapping("/home")
    public String index(Model model) {
        usersController.addCurrentUserToModel(model);       
        return "index";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        // Obtener información del usuario actual
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Si el usuario ya inició sesión, redirigir a la página de inicio
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/home";
        }
        // Si no, mostrar la página de inicio de sesión
        String errorMessage = request.getParameter("error");
        if (errorMessage != null) {
            model.addAttribute("errorMessage", "Invalid credentials");
        }
        return "pages/pages-login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }
}
