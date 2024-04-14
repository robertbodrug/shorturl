package com.elefants.shorturl.jwt.service;

import com.elefants.shorturl.exception.UserAlreadyExistException;
import com.elefants.shorturl.jwt.UserDetailsImpl;
import com.elefants.shorturl.users.Role;
import com.elefants.shorturl.users.UserEntity;
import com.elefants.shorturl.users.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Service
public class AuthenticationService  implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        GrantedAuthority authority = new SimpleGrantedAuthority(Role.USER.name());
        Collection<GrantedAuthority> authorities = Collections.singleton(authority);

        return UserDetailsImpl.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .authorities(authorities)
                .build();
    }


    @Transactional
    public void registerUser(String username, String password) throws UserAlreadyExistException {

        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistException(username);
        }

        UserEntity user = new UserEntity(username, encoder.encode(password));

        Set<Role> roleEntities = Collections.singleton(Role.USER);
        user.setRole(roleEntities.stream().findFirst().get());

        userRepository.save(user);
    }
}
