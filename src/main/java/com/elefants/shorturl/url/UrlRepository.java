package com.elefants.shorturl.url;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlEntity, Long> {
    // Additional query methods can be defined here
    @Query(nativeQuery = true, value = "SELECT * FROM url WHERE id = :id")
    UrlEntity getUrl(Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM url WHERE short_url = :shortUrl")
    UrlEntity findByShortUrl(String shortUrl);
}
