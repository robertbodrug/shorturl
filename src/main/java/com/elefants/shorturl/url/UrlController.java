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

    @PostMapping
    public UrlCreateResponse createUrl(/*Principal principal, */@RequestBody UrlCreateRequest request) {
        return urlService.create(/*principal.getName(),*/ request);
    }

    @GetMapping
    public UrlGetResponse getNote(@RequestParam(name = "id") Long id) {
        return urlService.getUrl(id);
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
