package com.example.nhungtrinhstore.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * Handles Angular routing (SPA fallback).
 * Routes without a file extension → forward to index.html (Angular client-side routing).
 * Routes with a file extension (.png, .js, .css...) → 404 (served by Spring static handler).
 */
@Controller
public class SpaController {

    @RequestMapping(value = "/**")
    public void forward(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String path = request.getRequestURI();
        // Static files (have extension like .png, .js, .css) - let Spring serve them
        if (path.matches(".*\\.[a-zA-Z0-9]+$")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        // Angular routes - serve index.html
        request.getRequestDispatcher("/index.html").forward(request, response);
    }
}


