package com.elefants.shorturl.jwt.controller;

import com.elefants.shorturl.exception.UserAlreadyExistException;
import com.elefants.shorturl.jwt.JwtUtils;
import com.elefants.shorturl.jwt.UserDetailsImpl;
import com.elefants.shorturl.jwt.request.LoginRequest;
import com.elefants.shorturl.jwt.request.SignUpRequest;
import com.elefants.shorturl.jwt.response.JwtResponse;
import com.elefants.shorturl.jwt.service.AuthenticationService;
import com.elefants.shorturl.users.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Validated
@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(loginRequest.getUsername());

        UserDetailsImpl userDetails = authenticationService.loadUserByUsername(jwtUtils.getUserNameFromJwtToken(jwt));
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok(new JwtResponse(jwt, userDetails.getUsername(), roles));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) throws UserAlreadyExistException {
        authenticationService.registerUser(signUpRequest.getUsername(),
                signUpRequest.getPassword());
        return ResponseEntity.accepted().build();
    }

}
