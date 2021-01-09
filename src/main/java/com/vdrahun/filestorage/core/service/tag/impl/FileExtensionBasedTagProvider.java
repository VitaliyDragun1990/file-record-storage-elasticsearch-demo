package com.vdrahun.filestorage.core.service.tag.impl;

import com.vdrahun.filestorage.core.service.tag.TagProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FileExtensionBasedTagProvider implements TagProvider {

    private final FilenameExtensionToTagProperties props;

    @Override
    public List<String> provideTagsBy(String filename) {
        String[] nameParts = filename.split("\\.");

        if (nameParts.length > 1) {
            return props.getTagsFor(nameParts[nameParts.length - 1]);
        }

        return List.of();
    }
}
