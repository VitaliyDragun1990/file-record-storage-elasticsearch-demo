package com.vdrahun.filestorage.core.service.search.internal;

import com.vdrahun.filestorage.core.domain.model.FileRecord;
import com.vdrahun.filestorage.core.service.search.BadRequestException;
import com.vdrahun.filestorage.core.service.search.FileRecordService;
import com.vdrahun.filestorage.core.service.search.ResourceNotFoundException;
import com.vdrahun.filestorage.core.service.search.model.FileRecordPage;
import com.vdrahun.filestorage.core.service.search.model.FileSearchRequest;
import com.vdrahun.filestorage.core.service.search.model.FileTagsRequest;
import com.vdrahun.filestorage.core.service.search.model.FileUploadRequest;
import com.vdrahun.filestorage.core.service.tag.TagProvider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileRecordServiceImpl implements FileRecordService {

    private static final String MESSAGE_FILE_NOT_FOUND = "File with id:[%s] not found";

    private final ElasticsearchOperations elasticsearchOperations;

    private final SearchQueryBuilder searchQueryBuilder;

    private final TagProvider tagProvider;

    @Override
    public String uploadFile(FileUploadRequest request) {
        List<String> implicitTags = tagProvider.provideTagsBy(request.name());

        FileRecord fileRecord = new FileRecord(request.name(), request.size(), implicitTags);

        FileRecord savedFileRecord = elasticsearchOperations.save(fileRecord);

        log.debug("New FileRecord created");

        return savedFileRecord.getId();
    }

    @Override
    public void deleteFile(String id) {
        if (elasticsearchOperations.exists(id, FileRecord.class)) {
            elasticsearchOperations.delete(id, FileRecord.class);

            log.debug("FileRecord with id:[{}] deleted", id);
        } else {
            throw new ResourceNotFoundException(MESSAGE_FILE_NOT_FOUND, id);
        }
    }

    @Override
    public void assignTagsToFile(FileTagsRequest request) {
        FileRecord fileRecord = elasticsearchOperations.get(request.fileRecordId(), FileRecord.class);

        if (fileRecord == null) {
            throw new ResourceNotFoundException(MESSAGE_FILE_NOT_FOUND, request.fileRecordId());
        }

        fileRecord.addTags(request.tags());
        elasticsearchOperations.save(fileRecord);
    }

    @Override
    public void removeTagsFromFile(FileTagsRequest request) {
        FileRecord fileRecord = elasticsearchOperations.get(request.fileRecordId(), FileRecord.class);

        if (fileRecord == null) {
            throw new ResourceNotFoundException(MESSAGE_FILE_NOT_FOUND, request.fileRecordId());
        }

        Collection<String> notAssignedTags = fileRecord.findNotAssignedTags(request.tags());
        if (notAssignedTags.isEmpty()) {
            fileRecord.removeTags(request.tags());

            elasticsearchOperations.save(fileRecord);

            log.debug("Tags:{} removed from FileRecord with id:[{}]", request.tags(), request.fileRecordId());
        } else {
            throw new BadRequestException("Tag(s) %s not found on file with id:[%s]", notAssignedTags, request.fileRecordId());
        }
    }

    @SneakyThrows
    @Override
    public FileRecordPage<FileRecord> searchFiles(FileSearchRequest request) {

        Query query = searchQueryBuilder.buildSearchQueryFrom(request);

        SearchHits<FileRecord> searchResult = elasticsearchOperations.search(query, FileRecord.class);

        SearchPage<FileRecord> searchHits = SearchHitSupport.searchPageFor(searchResult, query.getPageable());

        return new FileRecordPage<>(
                (int) searchHits.getTotalElements(),
                searchHits.getContent().stream().map(SearchHit::getContent).collect(toList()));
    }
}
