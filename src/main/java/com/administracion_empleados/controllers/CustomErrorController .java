package com.administracion_empleados.controllers;

import java.nio.file.AccessDeniedException;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        // Obtener el código de error HTTP y redirigir a la página de error
        // correspondiente
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "pages/pages-error-404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "pages/pages-error-500";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "pages/pages-error-403";
            }
        }
        return "error";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle404() {
        return "pages/pages-error-404";
    }

    @ExceptionHandler(value = { AccessDeniedException.class })
    public String handleAccessDeniedException(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException ex) {
        return "pages/pages-error-403";
    }
}