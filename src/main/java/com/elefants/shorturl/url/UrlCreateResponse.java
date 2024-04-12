package com.elefants.shorturl.url;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UrlCreateResponse {
    private Error error;

    private Long/*String*/ createdNoteId;

    public enum Error {
        OK,
        INVALID_LONGURL
    }

    public static UrlCreateResponse success(Long/*String*/ createdUrlId) {
        return builder().error(Error.OK).createdNoteId(createdUrlId).build();
    }

    public static UrlCreateResponse failed(Error error) {
        return builder().error(error).createdNoteId(-1L).build();
    }
}
