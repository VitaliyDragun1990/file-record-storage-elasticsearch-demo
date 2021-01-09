package com.vdrahun.filestorage.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.time.Duration;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties("elasticsearch")
public class ElasticsearchProperties {

    private final String[] hostAndPort;

    private final Duration socketTimeout;

    private final Duration connectionTimeout;
}
