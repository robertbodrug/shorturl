package com.elefants.shorturl.url;

import com.elefants.shorturl.users.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/url")
public class UrlController {
    private final UrlService urlService;

    @PostMapping("/create")
    public UrlCreateResponse createUrl(/*Principal principal,*/ @RequestBody UrlCreateRequest request) {
        return urlService.create(/*principal.getName(),*/ request);
    }

    @GetMapping
    public UrlGetResponse getUrl(/*Principal principal,*/ @RequestParam(name = "id") Long id) {
        return urlService.get(/*principal.getName(),*/ id);
    }

    @PatchMapping
    public UrlUpdateResponse update(/*Principal principal,*/ @RequestBody UrlUpdateRequest request) {
        return urlService.update(/*principal.getName(),*/ request);
    }

    @DeleteMapping
    public UrlDeleteResponse delete(/*Principal principal,*/ @RequestParam(name = "id") long id) {
        return urlService.delete(/*principal.getName(),*/ id);
    }

    @GetMapping("/{shortUrl}")
        public ResponseEntity<Void> getLongUrl(@PathVariable(name = "shortUrl")String shortUrl) {
        // Retrieve the long URL from the service
        String longUrl = urlService.getLongUrl(shortUrl);
        // Redirect to the long URL
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(longUrl))
                .build();
    }
}
