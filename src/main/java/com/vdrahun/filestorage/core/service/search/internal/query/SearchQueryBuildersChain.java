package com.vdrahun.filestorage.core.service.search.internal.query;

import com.vdrahun.filestorage.core.service.search.internal.SearchQueryBuilder;
import com.vdrahun.filestorage.core.service.search.model.FileSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

@Component
@Primary
public class SearchQueryBuildersChain implements SearchQueryBuilder {

    protected final SearchQueryBuilder next;

    @Autowired
    public SearchQueryBuildersChain(@Qualifier("tagsAndQueryBuilder") SearchQueryBuilder next) {
        this.next = next;
    }

    @Override
    public Query buildSearchQueryFrom(FileSearchRequest request) {
        return next.buildSearchQueryFrom(request);
    }
}
