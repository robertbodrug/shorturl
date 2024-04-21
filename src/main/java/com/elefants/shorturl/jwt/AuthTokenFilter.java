package com.elefants.shorturl.jwt;

import com.elefants.shorturl.users.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.util.StringUtils;


import java.io.IOException;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService service;



    @Override
    protected void doFilterInternal(@NonNull  HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(HEADER_NAME);
        if (authHeader == null || StringUtils.isEmpty(authHeader) || !authHeader.startsWith(BEARER_PREFIX)){
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader != null) {
            String jwt = authHeader.substring(BEARER_PREFIX.length());
            String username = jwtUtils.getUserNameFromJwtToken(jwt);
            if (io.micrometer.common.util.StringUtils.isNotEmpty(username) &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = service
                        .userDetailsService()
                        .loadUserByUsername(username);

                if (jwtUtils.isTokenValid(jwt, userDetails)) {
                    SecurityContext context = SecurityContextHolder.createEmptyContext();

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    context.setAuthentication(authToken);
                    SecurityContextHolder.setContext(context);

                    filterChain.doFilter(request, response);
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
