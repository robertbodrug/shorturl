package com.elefants.shorturl.users.dto.get;

import com.elefants.shorturl.users.UserEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GetAllUsersResponse {
    private Error error;

    private List<UserEntity> allUsers;

    public enum Error {
        ok
    }

    public static GetAllUsersResponse success(List<UserEntity> allUsers) {
        return builder().error(Error.ok).allUsers(allUsers).build();
    }

    public static GetAllUsersResponse failed(Error error) {

        return builder().error(error).allUsers(null).build();
    }
}