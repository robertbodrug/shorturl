package com.elefants.shorturl.url;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "url")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "short_url", nullable = false)
    String shortUrl;
    @Column(name = "long_url", nullable = false)
    String longUrl;
    @Column(name = "score", nullable = false)
    Long score;
    @Column(name = "is_active", nullable = false)
    Boolean isActive;

    //@ManyToOne
    //@JoinColumn(name="user_id", nullable=false)
    //private User user;
    //String userId;
}
