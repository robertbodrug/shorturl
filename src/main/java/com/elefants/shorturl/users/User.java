package com.elefants.shorturl.users;

import com.elefants.shorturl.url.UrlEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

public class User {
    @Id
    String id;
    String password;
    @OneToMany(mappedBy="user",cascade = CascadeType.REMOVE)
    List<UrlEntity> urls;
}
