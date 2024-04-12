package com.elefants.shorturl.users;

import com.elefants.shorturl.url.UrlEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    String id;
    String password;
    //@OneToMany(mappedBy="user",cascade = CascadeType.REMOVE)
    //List<UrlEntity> urls;
}
