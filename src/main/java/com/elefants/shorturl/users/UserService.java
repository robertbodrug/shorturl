package com.elefants.shorturl.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    public User findByUsername(String id) {
        Optional<User> user = repository.findById(id);

        if (user.isEmpty()) {
            return null;
        }

        return user.get();
    }
}
