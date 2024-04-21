package com.elefants.shorturl.url;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UrlUpdateResponse {
    private Error error;

    private Long id;
    private String shortUrl;
    private String longUrl;

    public enum Error {
        OK,
        INSUFFICIENT_PRIVILEGES,
        INVALID_URL_ID,
        INVALID_LONG_URL
    }

    public static UrlUpdateResponse success(UrlEntity updatedNote) {
        return builder().error(Error.OK)
                .id(updatedNote.getId())
                .shortUrl(updatedNote.getShortUrl())
                .longUrl(updatedNote.getLongUrl())
                .build();
    }

    public static UrlUpdateResponse failed(Error error) {
        return builder().error(error)
                .id(-1L)
                .shortUrl("Error")
                .longUrl("Error")
                .build();
    }
}
