package com.example.nhungtrinhstore.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handles Angular routing (SPA fallback).
 * Only forwards to index.html for routes WITHOUT a file extension.
 * Static files (.png, .js, .css, etc.) are served directly by Spring Boot.
 */
@Controller
public class SpaController {

    @RequestMapping(value = "/**")
    public String forward(HttpServletRequest request) {
        String path = request.getRequestURI();
        // If path has a file extension (e.g. .png, .js, .css), don't forward — let Spring serve it
        if (path.contains(".") && !path.endsWith(".html")) {
            return null; // Let Spring Boot handle static files normally
        }
        return "forward:/index.html";
    }
}

