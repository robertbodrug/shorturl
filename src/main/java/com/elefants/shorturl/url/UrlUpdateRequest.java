package com.elefants.shorturl.url;

import lombok.Data;

@Data
public class UrlUpdateRequest {
    private long id;
    private String longUrl;
}
