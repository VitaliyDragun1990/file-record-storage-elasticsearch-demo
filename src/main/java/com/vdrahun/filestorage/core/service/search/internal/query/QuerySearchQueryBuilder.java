package com.vdrahun.filestorage.core.service.search.internal.query;

import com.vdrahun.filestorage.core.service.search.internal.SearchQueryBuilder;
import com.vdrahun.filestorage.core.service.search.model.FileSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Handles search request when only query param is specified
 */
@Slf4j
@Component("queryBuilder")
public class QuerySearchQueryBuilder implements SearchQueryBuilder {

    private final SearchQueryBuilder next;

    @Autowired
    public QuerySearchQueryBuilder(@Qualifier("matchAllBuilder") SearchQueryBuilder next) {
        this.next = next;
    }

    @Override
    public Query buildSearchQueryFrom(FileSearchRequest request) {
        Optional<String> queryOptional = request.query();

        if (queryOptional.isPresent()) {
            log.debug("Building 'prefix' search query");

            return new NativeSearchQueryBuilder()
                    .withPageable(PageRequest.of(request.page(), request.size()))
                    .withQuery(QueryBuilders
                            .boolQuery()
                            .filter(QueryBuilders.prefixQuery("name", queryOptional.get().toLowerCase())))
                    .build();
        }

        return next.buildSearchQueryFrom(request);
    }
}
