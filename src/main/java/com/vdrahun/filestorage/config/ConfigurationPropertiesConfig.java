package com.vdrahun.filestorage.config;

import com.vdrahun.filestorage.FileStorageRestApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan(basePackageClasses = FileStorageRestApplication.class)
public class ConfigurationPropertiesConfig {

}
