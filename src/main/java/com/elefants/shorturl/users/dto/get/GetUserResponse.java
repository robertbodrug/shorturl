package com.elefants.shorturl.users.dto.get;

import com.elefants.shorturl.users.UserEntity;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetUserResponse {

    private Error error;

    private UserEntity user;

    public enum Error {
        ok,
        userNotFound
    }

    public static GetUserResponse success(UserEntity user) {
        return builder().error(Error.ok).user(user).build();
    }

    public static GetUserResponse failed(Error error) {
        return builder().error(error).user(null).build();
    }
}
