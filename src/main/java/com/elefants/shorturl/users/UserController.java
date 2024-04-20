package com.elefants.shorturl.users;

import com.elefants.shorturl.exception.UserAlreadyExistException;
import com.elefants.shorturl.jwt.JwtUtils;
import com.elefants.shorturl.jwt.UserDetailsImpl;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Validated
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<UserResponse> authenticateUser(@Valid @RequestBody CreateUserRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateJwtToken(loginRequest.getUsername());

        UserDetailsImpl userDetails = authenticationService.loadUserByUsername(jwtUtils.getUserNameFromJwtToken(jwtToken));
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok(new UserResponse(userDetails.getUsername(), roles));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody CreateUserRequest signUpRequest) throws UserAlreadyExistException {
        log.info("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(signUpRequest.getUsername(), signUpRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateJwtToken(signUpRequest.getUsername());

        UserDetailsImpl userDetails = authenticationService.loadUserByUsername(jwtUtils.getUserNameFromJwtToken(jwtToken));

        authenticationService.registerUser(signUpRequest);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{username}")
    public String getUser(@PathVariable(name = "username") String username){
        //TODO returning client
        return "";
    }

    @PostMapping
    public void createUser(@RequestBody CreateUserRequest request){
        //TODO validation and creating user
    }


    @PutMapping("/{username}")
    public String updateUser(@PathVariable(name = "username") String username,
    @RequestBody CreateUserRequest request){
        //TODO edit client
        return "";
    }
    @DeleteMapping
    public String deleteUser(@PathVariable(name = "username") String username){
        //TODO delete client
        return "";
    }
}
