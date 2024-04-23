package com.elefants.shorturl.users;

import com.elefants.shorturl.exception.UserAlreadyExistException;
import com.elefants.shorturl.jwt.UserDetailsImpl;
import com.elefants.shorturl.url.UrlEntity;
import com.elefants.shorturl.url.UrlRepository;
import com.elefants.shorturl.users.dto.create.CreateUserResponse;
import com.elefants.shorturl.users.dto.delete.DeleteUserResponse;
import com.elefants.shorturl.users.dto.get.GetAllUrlForUserResponse;
import com.elefants.shorturl.users.dto.get.GetAllUsersResponse;
import com.elefants.shorturl.users.dto.get.GetUserResponse;
import com.elefants.shorturl.users.dto.update.UpdateUserResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {


    private final PasswordEncoder encoder;
    private final int MIN_PASSWORD_LENGTH = 8;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UrlRepository urlRepository;

    public GetAllUrlForUserResponse getAllUrlForUserResponse(String username){

        List<UrlEntity> urls = urlRepository.findAllForUser(username);
        return GetAllUrlForUserResponse.success(urls);

    }

    public GetUserResponse getUser(String username) {

        Optional<UserEntity> optionalUser = userRepository.findById(username);

        if (optionalUser.isEmpty()) {
            return GetUserResponse.failed(GetUserResponse.Error.userNotFound);
        }

        return GetUserResponse.success(optionalUser.get());

    }

    public GetAllUsersResponse getAllUsers(){

        List<UserEntity> allUsers = userRepository.findAll();
        return GetAllUsersResponse.success(allUsers);

    }

    public UpdateUserResponse updateUser(UserRequest request){

        Optional<UserEntity> optionalUserEntity = userRepository.findById(request.getUsername());

        if(optionalUserEntity.isEmpty()){
            return UpdateUserResponse.failed(UpdateUserResponse.Error.invalidUsername);
        }

        Optional<UpdateUserResponse.Error> validationError = validateUpdateFields(request);

        if (validationError.isPresent()) {
            return UpdateUserResponse.failed(validationError.get());
        }

        UserEntity updatedEntity = userRepository.save(UserEntity.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build());

        userRepository.save(updatedEntity);

        return UpdateUserResponse.success(updatedEntity.getUsername());
    }

    public DeleteUserResponse deleteUser(String username){

        Optional<UserEntity> optionalUser = userRepository.findById(username);
        if(optionalUser.isEmpty()){
            return DeleteUserResponse.failed(DeleteUserResponse.Error.userNotFound);
        }

        UserEntity user = optionalUser.get();
        userRepository.delete(user);

        return DeleteUserResponse.success();

    }

    public UserEntity findByUsername(String id) {
        Optional<UserEntity> user = userRepository.findById(id);

        if (user.isEmpty()) {
            return null;
        }

        return user.get();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);

    }

    @Transactional
    public void registerUser(String username,
                             String password) throws UserAlreadyExistException {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistException(username);
        }

        UserEntity user = new UserEntity(username, encoder.encode(password));
        Set<RoleEntity> roleEntities = roleRepository.findByNames(Collections.singleton(UserRole.USER));
        user.setRoles(roleEntities);
        userRepository.save(user);

    }

    private Optional<CreateUserResponse.Error> validateCreateFields(UserRequest request) {

        if (Objects.isNull(request.getUsername())){
            return Optional.of(CreateUserResponse.Error.invalidUsername);
        }

        if (getUser(request.getUsername())!=null){
            return Optional.of(CreateUserResponse.Error.userAlreadyExists);
        }

        if (request.getPassword().length() < MIN_PASSWORD_LENGTH || passwordValidator(request.getPassword())){
            return Optional.of(CreateUserResponse.Error.invalidPassword);
        }

        return Optional.empty();

    }

    private Optional<UpdateUserResponse.Error> validateUpdateFields(UserRequest request) {

        if (Objects.nonNull(request.getUsername())) {
            return Optional.of(UpdateUserResponse.Error.invalidUsername);
        }

        if (request.getPassword().length() < MIN_PASSWORD_LENGTH || passwordValidator(request.getPassword())){
            return Optional.of(UpdateUserResponse.Error.invalidPassword);
        }

        return Optional.empty();

    }

    private boolean passwordValidator(String password) {

        boolean hasDigit = false;
        boolean hasUpper = false;
        boolean hasLower = false;

        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isLowerCase(c)) {
                hasLower = true;
            }

            if (hasDigit && hasUpper && hasLower) {
                return true;
            }
        }

        return hasDigit && hasUpper && hasLower;

    }
}
