package com.example.nhungtrinhstore.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handles Angular routing (SPA fallback).
 * Any request that doesn't match an API endpoint will serve index.html,
 * allowing Angular to handle client-side routing.
 */
@Controller
public class SpaController {

    @RequestMapping(value = {"/", "/{path:[^\\.]*}", "/{path:[^\\.]*}/**"})
    public String forward() {
        return "forward:/index.html";
    }
}
