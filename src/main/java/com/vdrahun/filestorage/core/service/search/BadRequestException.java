package com.vdrahun.filestorage.core.service.search;

import com.vdrahun.filestorage.core.exception.FileStorageApplicationException;

public class BadRequestException extends FileStorageApplicationException {

    public BadRequestException(String messageTemplate, Object... args) {
        super(messageTemplate, args);
    }
}
