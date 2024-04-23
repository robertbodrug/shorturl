package com.elefants.shorturl.users.dto.get;

import com.elefants.shorturl.url.UrlEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GetAllUrlForUserResponse {
    private Error error;

    private List<UrlEntity> allUrlForUser;

    public enum Error {
        ok
    }

    public static GetAllUrlForUserResponse success(List<UrlEntity> allUrlForUser) {
        return builder().error(Error.ok).allUrlForUser(allUrlForUser).build();
    }

    public static GetAllUrlForUserResponse failed(Error error) {

        return builder().error(error).allUrlForUser(null).build();
    }
}
