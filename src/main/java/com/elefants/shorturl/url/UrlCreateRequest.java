package com.elefants.shorturl.url;

import lombok.Data;

@Data
public class UrlCreateRequest {
    private String shortUrl;
    private String longUrl;
    private Long score;
    private Boolean isActive;
    //private String userId;
}
