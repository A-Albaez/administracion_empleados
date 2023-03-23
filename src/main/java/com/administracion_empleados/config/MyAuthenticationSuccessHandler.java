package com.administracion_empleados.config;

import java.io.IOException;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler
{
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
                Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
                if (roles.contains("admin"))
                {
                    request.getSession(false).setMaxInactiveInterval(1800);
                }
                else
                {
                    request.getSession(false).setMaxInactiveInterval(1800);
                }
                //Your login success url goes here, currently login success url="/"
                response.sendRedirect("/home");
    }
}
