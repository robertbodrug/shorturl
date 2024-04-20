package com.elefants.shorturl.url;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class UrlCreateRequest {
    private String longUrl;
}
