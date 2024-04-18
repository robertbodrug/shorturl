package com.elefants.shorturl.users;

import com.elefants.shorturl.url.UrlEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }
    @OneToMany(mappedBy="user",cascade = CascadeType.ALL)
    private List<UrlEntity> urls;
}
