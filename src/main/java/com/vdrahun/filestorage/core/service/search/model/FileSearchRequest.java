package com.vdrahun.filestorage.core.service.search.model;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Accessors(fluent = true)
public class FileSearchRequest {

    private final String query;

    private final Set<String> tags = new HashSet<>();

    @Getter
    private final int page;

    @Getter
    private final int size;

    public FileSearchRequest(String query, List<String> tags, int page, int size) {
        this.query = StringUtils.hasText(query) ? query : null;
        Optional.ofNullable(tags).ifPresent(this.tags::addAll);
        this.page = page;
        this.size = size;
    }

    public Optional<String> query() {
        return Optional.ofNullable(query);
    }

    public List<String> tags() {
        return List.copyOf(tags);
    }

}
