package com.elefants.shorturl.url;

import com.elefants.shorturl.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UserService userService;
    private final UrlRepository repository;
    public UrlCreateResponse create(/*String id, */UrlCreateRequest request) {
        Optional<UrlCreateResponse.Error> validationError = validateCreateFields(request);

        if (validationError.isPresent()) {
            return UrlCreateResponse.failed(validationError.get());
        }

        //User user = userService.findByUsername(id);

        UrlEntity createdUrl = repository.save(UrlEntity.builder()
                .shortUrl(generateShortUrl())
                .longUrl(request.getLongUrl())
                .score(0L)
                .isActive(true)
                .build());

        return UrlCreateResponse.success(createdUrl.getId());
    }

    public UrlGetResponse getUrl(Long id) {
        Optional<UrlEntity> optionalUrl = repository.findById(id);

        if (optionalUrl.isEmpty()) {
            return UrlGetResponse.failed(UrlGetResponse.Error.INVALID_URL_ID);
        }

        UrlEntity url = optionalUrl.get();

        repository.getUrl(url.getId());

        return UrlGetResponse.success(url);
    }

    public String getLongUrl(String shortUrl) {

        UrlEntity url = repository.findByShortUrl(shortUrl);
        url.setScore(url.getScore() + 1);
        repository.save(url);

        return url.getLongUrl();
    }

    private String generateShortUrl() {
        int length = new Random().nextInt(3) + 6; // Generate length between 6 and 8
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder shortLink = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = new Random().nextInt(characters.length());
            shortLink.append(characters.charAt(index));
        }
        return shortLink.toString();
    }

    private Optional<UrlCreateResponse.Error> validateCreateFields(UrlCreateRequest request) {
        try {
            URL link = new URL(request.getLongUrl());
            HttpURLConnection connection = (HttpURLConnection) link.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            int responseCode = connection.getResponseCode();
            if(!(responseCode >= 200 && responseCode < 300)){
                return Optional.of(UrlCreateResponse.Error.INVALID_LONGURL);
            }
        } catch (IOException e) {
            return Optional.of(UrlCreateResponse.Error.INVALID_LONGURL);
        }
        if (Objects.isNull(request.getLongUrl())) {
            return Optional.of(UrlCreateResponse.Error.INVALID_LONGURL);
        }
        return Optional.empty();
    }
}
