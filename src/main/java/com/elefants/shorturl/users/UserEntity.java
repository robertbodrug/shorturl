package com.elefants.shorturl.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "user")
public class UserEntity {
    @Id
    String username;
    String password;
    @Enumerated(EnumType.STRING)
    Role role;

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }
    //@OneToMany(mappedBy="user",cascade = CascadeType.REMOVE)
    //List<UrlEntity> urls;
}
