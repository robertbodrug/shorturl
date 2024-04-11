package com.elefants.shorturl.url;

import com.elefants.shorturl.users.CreateUserRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UrlController {
@GetMapping("/{shortUrl}")
    public String getLongUrl(@PathVariable(name = "shortUrl")String shortUrl){
    //TODO main logic with reconnect
    return "";
}
    @PostMapping
    public void createUrl(@RequestBody UrlCreateRequest request){
        //TODO validation and creating user
    }
}
