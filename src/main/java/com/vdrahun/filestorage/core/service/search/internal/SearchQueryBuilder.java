package com.vdrahun.filestorage.core.service.search.internal;

import com.vdrahun.filestorage.core.service.search.model.FileSearchRequest;
import org.springframework.data.elasticsearch.core.query.Query;

public interface SearchQueryBuilder {

    Query buildSearchQueryFrom(FileSearchRequest request);
}
