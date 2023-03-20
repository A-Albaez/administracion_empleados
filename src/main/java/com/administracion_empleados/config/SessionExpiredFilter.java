package com.administracion_empleados.config;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SessionExpiredFilter  {
 
    // @Override
    // protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
 
    //     if (request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid()) {
    //         SecurityContextHolder.clearContext(); // Cerrar sesión
    //         response.sendRedirect(request.getContextPath() + "/login?expired=true"); // Redirigir a la página de inicio con un parámetro para mostrar una alerta
    //         return;
    //     }
 
    //     filterChain.doFilter(request, response);
    // }
}
