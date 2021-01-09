package com.vdrahun.filestorage.core.service.search.internal.query;

import com.vdrahun.filestorage.core.service.search.internal.SearchQueryBuilder;
import com.vdrahun.filestorage.core.service.search.model.FileSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * Handles search request when no params are specified
 */
@Slf4j
@Component("matchAllBuilder")
public class MatchAllSearchQueryBuilder implements SearchQueryBuilder {

    @Override
    public Query buildSearchQueryFrom(FileSearchRequest request) {
        log.debug("Building 'match_all' search query");

        return new NativeSearchQueryBuilder()
                .withPageable(PageRequest.of(request.page(), request.size()))
                .withQuery(QueryBuilders.matchAllQuery())
                .build();
    }
}
