package com.vdrahun.filestorage.config;

import com.vdrahun.filestorage.FileStorageRestApplication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Slf4j
@RequiredArgsConstructor
@EnableElasticsearchRepositories(basePackageClasses = FileStorageRestApplication.class)
@Configuration
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

    private final ElasticsearchProperties elasticProps;

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(elasticProps.hostAndPort())
                .withConnectTimeout(elasticProps.connectionTimeout())
                .withSocketTimeout(elasticProps.socketTimeout())
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
}
