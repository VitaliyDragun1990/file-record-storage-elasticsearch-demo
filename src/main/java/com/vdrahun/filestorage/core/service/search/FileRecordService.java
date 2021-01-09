package com.vdrahun.filestorage.core.service.search;

import com.vdrahun.filestorage.core.domain.model.FileRecord;
import com.vdrahun.filestorage.core.service.search.model.FileRecordPage;
import com.vdrahun.filestorage.core.service.search.model.FileSearchRequest;
import com.vdrahun.filestorage.core.service.search.model.FileTagsRequest;
import com.vdrahun.filestorage.core.service.search.model.FileUploadRequest;

public interface FileRecordService {

    String uploadFile(FileUploadRequest request);

    void deleteFile(String id);

    void assignTagsToFile(FileTagsRequest request);

    void removeTagsFromFile(FileTagsRequest request);

    FileRecordPage<FileRecord> searchFiles(FileSearchRequest request);
}
