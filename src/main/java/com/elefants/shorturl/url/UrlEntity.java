package com.elefants.shorturl.url;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.elefants.shorturl.users.UserEntity;

@Entity
@Table(name = "urls")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlEntity {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_url", nullable = false)
    private String shortUrl;

    @Column(name = "long_url", nullable = false)
    private String longUrl;

    @Column(name = "score", nullable = false)
    private Long score;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name="username", referencedColumnName = "username", nullable=false)
    private UserEntity user;
}