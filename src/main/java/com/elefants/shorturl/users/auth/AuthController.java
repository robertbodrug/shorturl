package com.elefants.shorturl.users.auth;

import com.elefants.shorturl.jwt.JwtUtils;
import com.elefants.shorturl.users.CreateUserRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authService;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid CreateUserRequest request){
        String jwt = jwtUtils.generateJwtToken(authService.login(request));
        return ResponseEntity.ok().header("Authorization", jwt).build();
    }
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody @Valid CreateUserRequest request){
        String jwt = jwtUtils.generateJwtToken(authService.registerUser(request));
        return ResponseEntity.ok().header("Authorization",jwt).build();
    }

//    @PostMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response) throws ServletException {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null) {
//            request.getSession().invalidate();
//        }
//        Cookie[] cookies = request.getCookies();
//
//        if (cookies != null){
//            for (Cookie cookie : cookies){
//                String cookieName = cookie.getName();
//                Cookie cookieToDelete = new Cookie(cookieName, null);
//                cookieToDelete.setMaxAge(0);
//                response.addCookie(cookieToDelete);
//            }
//        }
//
//        return "redirect:/";
//    }

//    private String invalidateSession(HttpServletRequest request, HttpServletResponse response){
//        Cookie[] cookies = request.getCookies();
//
//        if (cookies != null){
//            for (Cookie cookie : cookies){
//                String cookieName = cookie.getName();
//                Cookie cookieToDelete = new Cookie(cookieName, null);
//                cookieToDelete.setMaxAge(0);
//                response.addCookie(cookieToDelete);
//            }
//        }
//        return "deleted cookie";
//    }

}
