package com.elefants.shorturl.users.dto.update;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateUserResponse {

    private Error error;

    private String updatedUsername;

    public enum Error {
        ok,
        invalidUsername,
        invalidPassword
    }

    public static UpdateUserResponse success(String updatedUsername) {
        return builder().error(Error.ok).updatedUsername(updatedUsername).build();
    }

    public static UpdateUserResponse failed(Error error) {
        return builder().error(error).updatedUsername("").build();
    }

}