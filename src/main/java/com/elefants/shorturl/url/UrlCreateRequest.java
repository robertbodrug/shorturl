package com.elefants.shorturl.url;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class UrlCreateRequest {
    //private String shortUrl;
    private String longUrl;
    //private Long score;
    //private Boolean isActive;
    //private String userId;

    /*public UrlCreateRequest(String longUrl) {
        this.longUrl = longUrl;
    }*/
}
