package com.vdrahun.filestorage.init;

import com.vdrahun.filestorage.core.domain.model.FileRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ElasticsearchInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final ElasticsearchOperations elasticsearchTemplate;

    private final boolean initializeIndex;

    public ElasticsearchInitializer(
            ElasticsearchOperations elasticsearchTemplate,
            @Value("${elasticsearch.index.initialize}") boolean initializeIndex) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.initializeIndex = initializeIndex;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        if (initializeIndex) {
            log.info("Starting elasticsearch index reinitializing process...");

            IndexOperations productIndexOps = elasticsearchTemplate.indexOps(FileRecord.class);

            productIndexOps.delete();
            log.info("Elasticsearch index(s) deleted");

            productIndexOps.create();
            log.info("Elasticsearch index(s) created");

            productIndexOps.createMapping();
            log.info("Elasticsearch mappings created");

            productIndexOps.putMapping();
            log.info("Elasticsearch mappings put to the index");

            log.info("Elasticsearch index has been reinitialized.");
        }
    }
}
