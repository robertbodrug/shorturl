package com.elefants.shorturl.url;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class UrlEntity {
    @Id
    String id;
    String longUrl;
    Long score;
    Boolean isActive;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    String userId;
}
