package com.elefants.shorturl.users;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @GetMapping("/{username}")
    public String getUser(@PathVariable(name = "username") String username){
        //TODO returning client
        return "";
    }

    @PostMapping
    public void createUser(@RequestBody CreateUserRequest request){
        //TODO validation and creating user
    }
//    @GetMapping("/login")
//    public String loginUser(@RequestParam(name = "username") String username){
//        TODO returning client
//        return "";
//    }
//

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
