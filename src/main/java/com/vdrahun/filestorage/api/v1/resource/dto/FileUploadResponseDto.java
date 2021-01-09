package com.vdrahun.filestorage.api.v1.resource.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileUploadResponseDto {

    @JsonProperty("ID")
    private String id;
}
