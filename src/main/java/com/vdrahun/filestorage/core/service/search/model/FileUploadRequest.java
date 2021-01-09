package com.vdrahun.filestorage.core.service.search.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class FileUploadRequest {

    private final String name;

    private final Long size;

}
