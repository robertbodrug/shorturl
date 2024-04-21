package com.elefants.shorturl.users;

import com.elefants.shorturl.exception.UserAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public UserEntity createUser(UserEntity user){
        if (repository.existsByUsername(user.getUsername())){
            throw new UserAlreadyExistException(user.getUsername());
        }
        return repository.save(user);
    }

    public UserDetailsService userDetailsService(){
        return this::findByUsername;
    }

    public UserEntity findByUsername(String id)  {
            return repository.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException("User with username: " + id + ", not found"));
    }
}
