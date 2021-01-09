package com.vdrahun.filestorage.api.v1.resource.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

    private final boolean success;

    private final String error;

    private final String path;

    private final LocalDateTime timestamp;

    public static ApiResponse success() {
        return new ApiResponse(true, null, null, null);
    }

    public static ApiResponse failure(String error, String path, LocalDateTime timestamp) {
        return new ApiResponse(false, error, path, timestamp);
    }
}
