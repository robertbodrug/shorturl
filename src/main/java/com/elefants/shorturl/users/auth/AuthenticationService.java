package com.elefants.shorturl.users.auth;

import com.elefants.shorturl.exception.UserAlreadyExistException;
import com.elefants.shorturl.users.CreateUserRequest;
import com.elefants.shorturl.users.Role;
import com.elefants.shorturl.users.UserEntity;
import com.elefants.shorturl.users.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final PasswordEncoder encoder;
    private final UserService service;
    private final AuthenticationManager authenticationManager;



    public UserDetails login(CreateUserRequest request) throws UsernameNotFoundException {

        UserDetails userDetails = service.userDetailsService().loadUserByUsername(request.getUsername());

        if(Objects.equals(encoder.encode(request.getPassword()), encoder.encode(userDetails.getPassword()))){

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            ));
        }
        return userDetails;
    }


    @Transactional
    public UserEntity registerUser(CreateUserRequest signUpRequest) throws UserAlreadyExistException {

        UserEntity entity = UserEntity.builder()
                .username(signUpRequest.getUsername())
                .password(encoder.encode(signUpRequest.getPassword()))
                .role(Role.USER)
                .build();

        service.createUser(entity);

        return entity;
    }
}
