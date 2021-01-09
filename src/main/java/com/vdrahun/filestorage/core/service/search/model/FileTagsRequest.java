package com.vdrahun.filestorage.core.service.search.model;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Accessors(fluent = true)
public class FileTagsRequest {

    private final String fileRecordId;

    private final Set<String> tags = new HashSet<>();

    public FileTagsRequest(String fileRecordId, List<String> tags) {
        this.fileRecordId = fileRecordId;
        this.tags.addAll(tags);
    }
}
