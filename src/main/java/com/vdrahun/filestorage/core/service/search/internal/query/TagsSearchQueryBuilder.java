package com.vdrahun.filestorage.core.service.search.internal.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vdrahun.filestorage.core.service.search.internal.SearchQueryBuilder;
import com.vdrahun.filestorage.core.service.search.model.FileSearchRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Handles search request when only tags params are specified
 */
@Slf4j
@Component("tagsBuilder")
public class TagsSearchQueryBuilder implements SearchQueryBuilder {

    private final SearchQueryBuilder next;

    private final ObjectMapper mapper;

    @Autowired
    public TagsSearchQueryBuilder(@Qualifier("queryBuilder") SearchQueryBuilder next, ObjectMapper mapper) {
        this.next = next;
        this.mapper = mapper;
    }

    @SneakyThrows
    @Override
    public Query buildSearchQueryFrom(FileSearchRequest request) {
        List<String> searchTags = request.tags();

        if (!searchTags.isEmpty()) {
            log.debug("Building 'terms_set' search query");

            TermsSetTemplate termsSet = TermsSetTemplate.forTags(request.tags());
            String termsSetClause = mapper.writeValueAsString(termsSet);

            return new NativeSearchQueryBuilder()
                    .withPageable(PageRequest.of(request.page(), request.size()))
                    .withQuery(QueryBuilders
                            .boolQuery()
                            .filter(QueryBuilders.wrapperQuery(termsSetClause)))
                    .build();
        }

        return next.buildSearchQueryFrom(request);
    }
}
