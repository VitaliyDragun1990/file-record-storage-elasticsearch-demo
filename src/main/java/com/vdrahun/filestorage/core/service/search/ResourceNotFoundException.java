package com.vdrahun.filestorage.core.service.search;

import com.vdrahun.filestorage.core.exception.FileStorageApplicationException;

public class ResourceNotFoundException extends FileStorageApplicationException {

    public ResourceNotFoundException(String messageTemplate, Object... args) {
        super(messageTemplate, args);
    }
}
