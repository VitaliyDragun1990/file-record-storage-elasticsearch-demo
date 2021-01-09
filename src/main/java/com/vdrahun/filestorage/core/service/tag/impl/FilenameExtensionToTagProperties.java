package com.vdrahun.filestorage.core.service.tag.impl;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties("filename")
public class FilenameExtensionToTagProperties {

    private final Map<String, List<String>> extensionMap;

    public List<String> getTagsFor(String extension) {
        return extensionMap.getOrDefault(extension.toLowerCase(), List.of());
    }
}
