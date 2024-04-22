package com.elefants.shorturl.url;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UrlDeleteResponse {
    private Error error;

    public enum Error {
        OK,
        INSUFFICIENT_PRIVILEGES,
        INVALID_URL_ID
    }

    public static UrlDeleteResponse success() {
        return builder().error(Error.OK).build();
    }

    public static UrlDeleteResponse failed(Error error) {
        return builder().error(error).build();
    }
}
