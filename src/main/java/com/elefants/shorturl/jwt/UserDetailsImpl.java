package com.elefants.shorturl.jwt;

import com.elefants.shorturl.users.Role;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class UserDetailsImpl implements UserDetails {

    private String username;
    private String password;
    private Role role; // Поле для ролі користувача

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Builder
    public UserDetailsImpl(String username, String password, Role role, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.authorities = authorities;
    }

    public UserDetailsImpl( String username,
                           Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Повертаємо одну роль користувача як колекцію GrantedAuthority
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
