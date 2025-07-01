package com.escuela.gestion.controller;

import com.escuela.gestion.dto.LoginRequest;
import com.escuela.gestion.dto.LoginResponse;
import com.escuela.gestion.entity.Usuario;
import com.escuela.gestion.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), 
                    loginRequest.getPassword()
                )
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        Usuario usuario = (Usuario) authentication.getPrincipal();

        LoginResponse loginResponse = new LoginResponse(
            jwt, 
            usuario.getUsername(), 
            "ADMIN"  
        );
        
        return ResponseEntity.ok(loginResponse);
    }
}