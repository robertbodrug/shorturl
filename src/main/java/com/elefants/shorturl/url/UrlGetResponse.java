package com.elefants.shorturl.url;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UrlGetResponse {
    private Error error;

    private UrlEntity url;

    public enum Error {
        OK,
        INVALID_URL_ID
    }

    public static UrlGetResponse success(UrlEntity url) {
        return builder().error(Error.OK).url(url).build();
    }

    public static UrlGetResponse failed(Error error) {
        return builder().error(error).url(null).build();
    }
}
