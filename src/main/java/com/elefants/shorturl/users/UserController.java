package com.elefants.shorturl.users;

import com.elefants.shorturl.users.dto.delete.DeleteUserResponse;
import com.elefants.shorturl.users.dto.get.GetAllUrlForUserResponse;
import com.elefants.shorturl.users.dto.get.GetAllUsersResponse;
import com.elefants.shorturl.users.dto.update.UpdateUserResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/user")
public class UserController {

    private UserService userService;
//, відсутність токену авторизації в заголовку запиту
    @DeleteMapping("/user/{username}")
    public DeleteUserResponse deleteUser(@PathVariable(name = "username") String username){
        return userService.deleteUser(username);
    }

    @PutMapping("/user/{username}")
    public UpdateUserResponse updateUser(@PathVariable(name = "username") String username,
                                         @RequestBody UserRequest request){
        return userService.updateUser(request);
    }

    @GetMapping("/user/{username}")
    public GetAllUrlForUserResponse getAllUrlForUser(@PathVariable(name = "username") String username){
        return userService.getAllUrlForUserResponse(username);
    }

     @GetMapping("/users")
     public GetAllUsersResponse getAllUsers(){
        return userService.getAllUsers();
     }
}
