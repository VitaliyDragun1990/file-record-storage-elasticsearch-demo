package com.vdrahun.filestorage.core.exception;

import static java.lang.String.format;

public class FileStorageApplicationException extends RuntimeException {

    public FileStorageApplicationException(String messageTemplate, Object... args) {
        super(format(messageTemplate, args));
    }

    public FileStorageApplicationException(Throwable cause, String messageTemplate, Object... args) {
        super(format(messageTemplate, args), cause);
    }
}
