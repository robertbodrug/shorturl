package com.elefants.shorturl.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    public UserEntity findByUsername(String id) {
        Optional<UserEntity> user = repository.findById(id);

        if (user.isEmpty()) {
            return null;
        }

        return user.get();
    }
}
