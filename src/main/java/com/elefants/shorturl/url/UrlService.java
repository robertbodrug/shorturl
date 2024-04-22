package com.elefants.shorturl.url;

import com.elefants.shorturl.users.UserEntity;
import com.elefants.shorturl.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    private Long idNum = 5L; // should be destroyed when second migration will be deleted
    public UrlCreateResponse create(/*String id,*/ UrlCreateRequest request) {
        Optional<UrlCreateResponse.Error> validationError = validateCreateFields(request);

        if (validationError.isPresent()) {
            return UrlCreateResponse.failed(validationError.get());
        }

        //UserEntity user = userService.findByUsername(id);
        String id = "user1"; // created for test, when security task will finish, this line should be destroyed
        UserEntity user = userService.findByUsername(id);


        UrlEntity createdUrl = repository.save(UrlEntity.builder()
                .id(idNum++)
                .user(user)
                .shortUrl(generateShortUrl())
                .longUrl(request.getLongUrl())
                .score(0L)
                .isActive(true)
                .build());

        return UrlCreateResponse.success(createdUrl.getId(), createdUrl.getShortUrl());
    }

    public UrlGetResponse get(/*String username,*/ Long id) {
        Optional<UrlEntity> optionalUrl = repository.findById(id);

        if (optionalUrl.isEmpty()) {
            return UrlGetResponse.failed(UrlGetResponse.Error.INVALID_URL_ID);
        }

        /*boolean isNotUserUrl = isNotUserNote(username, url);

        if (isNotUserUrl) {
            return UrlDeleteResponse.failed(UrlDeleteResponse.Error.INSUFFICIENT_PRIVILEGES);
        }*/

        UrlEntity url = optionalUrl.get();

        repository.getUrl(url.getId());

        return UrlGetResponse.success(url);
    }

    public UrlUpdateResponse update(/*String username,*/ UrlUpdateRequest request) {
        Optional<UrlEntity> optionalNote = repository.findById(request.getId());

        if (optionalNote.isEmpty()) {
            return UrlUpdateResponse.failed(UrlUpdateResponse.Error.INVALID_URL_ID);
        }

        UrlEntity url = optionalNote.get();

        /*boolean isNotUserUrl = isNotUserUrl(username, url);

        if (isNotUserUrl) {
            return UrlUpdateResponse.failed(UrlUpdateResponse.Error.INSUFFICIENT_PRIVILEGES);
        }*/

        Optional<UrlUpdateResponse.Error> validationError = validateUpdateFields(request);

        if (validationError.isPresent()) {
            return UrlUpdateResponse.failed(validationError.get());
        }

        url.setLongUrl(request.getLongUrl());
        url.setShortUrl(generateShortUrl());
        url.setScore(0L);
        url.setIsActive(true);

        repository.save(url);

        return UrlUpdateResponse.success(url);
    }

    public UrlDeleteResponse delete(/*String username,*/ long id) {
        Optional<UrlEntity> optionalNote = repository.findById(id);

        if (optionalNote.isEmpty()) {
            return UrlDeleteResponse.failed(UrlDeleteResponse.Error.INVALID_URL_ID);
        }

        UrlEntity url = optionalNote.get();

        /*boolean isNotUserUrl = isNotUserNote(username, url);

        if (isNotUserUrl) {
            return UrlDeleteResponse.failed(UrlDeleteResponse.Error.INSUFFICIENT_PRIVILEGES);
        }*/

        repository.delete(url);

        return UrlDeleteResponse.success();
    }

    public String getLongUrl(String shortUrl) {

        UrlEntity url = repository.findByShortUrl(shortUrl);
        url.setScore(url.getScore() + 1);
        repository.save(url);

        return url.getLongUrl();
    }

    public String generateShortUrl() {
        int length = new Random().nextInt(3) + 6; // Generate length between 6 and 8
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder shortLink = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = new Random().nextInt(characters.length());
            shortLink.append(characters.charAt(index));
        }
        return shortLink.toString();
    }

    public Optional<UrlCreateResponse.Error> validateCreateFields(UrlCreateRequest request) {
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

    public Optional<UrlUpdateResponse.Error> validateUpdateFields(UrlUpdateRequest request) {
        try {
            URL link = new URL(request.getLongUrl());
            HttpURLConnection connection = (HttpURLConnection) link.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            int responseCode = connection.getResponseCode();
            if(!(responseCode >= 200 && responseCode < 300)){
                return Optional.of(UrlUpdateResponse.Error.INVALID_LONG_URL);
            }
        } catch (IOException e) {
            return Optional.of(UrlUpdateResponse.Error.INVALID_LONG_URL);
        }
        if (Objects.isNull(request.getLongUrl())) {
            return Optional.of(UrlUpdateResponse.Error.INVALID_LONG_URL);
        }
        return Optional.empty();
    }

    private boolean isNotUserUrl(String username, UrlEntity url) {
        return !url.getUser().getUsername().equals(username);
    }
}
