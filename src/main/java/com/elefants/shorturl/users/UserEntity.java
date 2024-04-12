package com.elefants.shorturl.users;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    String username;
    String password;
    @Enumerated(EnumType.STRING)
    Role role;
    //@OneToMany(mappedBy="user",cascade = CascadeType.REMOVE)
    //List<UrlEntity> urls;
}
