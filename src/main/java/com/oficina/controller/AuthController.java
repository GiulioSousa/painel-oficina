package com.oficina.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/me")
    public ResponseEntity<Void> me(Authentication authentication) {
        if (authentication == null 
            || !authentication.isAuthenticated()
            ||authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok().build();
    }
}