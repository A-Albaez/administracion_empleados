package com.administracion_empleados.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestContextHolder;

import com.administracion_empleados.models.User;
import com.administracion_empleados.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.ServletRequestAttributes;


@Controller
public class HomeController {
    @Autowired
    private UserService uService;

    @GetMapping("/home")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        // Si el usuario ya inició sesión, redirigir a la página de inicio
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            model.addAttribute("employee", uService.findAll());
            return "redirect:/home";
        }
        // Si no, mostrar la página de inicio de sesión
        String errorMessage = request.getParameter("error");
        if (errorMessage != null) {
            model.addAttribute("errorMessage", "Invalid credentials");
        }

        // Agregar el atributo al header después de la autenticación exitosa
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        if (response != null && response.isCommitted() == false) {
            String username = auth.getName();
            response.addHeader("X-Username", username);
            User user = uService.findUserByEmail(username); // Obtener el objeto Employee correspondiente
                                                                           // al usuario autenticado
            model.addAttribute("employee", user); // Agregar el objeto Employee al modelo
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
