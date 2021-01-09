package com.vdrahun.filestorage.api.v1.resource;

import com.vdrahun.filestorage.api.v1.resource.dto.ApiResponse;
import com.vdrahun.filestorage.api.v1.resource.dto.FileRecordDto;
import com.vdrahun.filestorage.api.v1.resource.dto.FileUploadRequestDto;
import com.vdrahun.filestorage.api.v1.resource.dto.FileUploadResponseDto;
import com.vdrahun.filestorage.config.ApiVersion;
import com.vdrahun.filestorage.core.domain.model.FileRecord;
import com.vdrahun.filestorage.core.service.search.FileRecordService;
import com.vdrahun.filestorage.core.service.search.model.FileRecordPage;
import com.vdrahun.filestorage.core.service.search.model.FileSearchRequest;
import com.vdrahun.filestorage.core.service.search.model.FileTagsRequest;
import com.vdrahun.filestorage.core.service.search.model.FileUploadRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(FileResource.BASE_URL)
@Validated
public class FileResource {

    static final String BASE_URL = ApiVersion.VERSION_1 + "/file";

    private final FileRecordService fileRecordService;

    private final ModelMapper mapper;

    @PutMapping
    public FileUploadResponseDto uploadFile(@Valid @RequestBody FileUploadRequestDto requestDto) {
        log.debug("Received PUT request to upload new file:[{}]", requestDto);

        String fileId = fileRecordService.uploadFile(new FileUploadRequest(requestDto.getName(), requestDto.getSize()));

        return new FileUploadResponseDto(fileId);
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteFile(@PathVariable("id") @NotBlank String fileId) {
        log.debug("Received DELETE request to delete file with id:[{}]", fileId);

        fileRecordService.deleteFile(fileId);

        return ApiResponse.success();
    }

    @PostMapping("/{id}/tags")
    public ApiResponse assignTags(@PathVariable("id") String fileId, @RequestBody @NotEmpty List<String> tags) {
        log.debug("Received POST request to assign tags to file with id:[{}]", fileId);

        fileRecordService.assignTagsToFile(new FileTagsRequest(fileId, tags));

        return ApiResponse.success();
    }

    @DeleteMapping("/{id}/tags")
    public ApiResponse removeTags(@PathVariable("id") String fileId, @RequestBody @NotEmpty List<String> tags) {
        log.debug("Received DELETE request to remove tags from file with id:[{}]", fileId);

        fileRecordService.removeTagsFromFile(new FileTagsRequest(fileId, tags));

        return ApiResponse.success();
    }

    @GetMapping
    public FileRecordPage<FileRecordDto> listFiles(
            @RequestParam(name = "tags", required = false) List<String> tags,
            @RequestParam(name = "q", required = false) String query,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        log.debug("Received GET request to list files by tags:{}, query:[{}], page:[{}], size:[{}]", tags, query, page, size);

        FileRecordPage<FileRecord> response = fileRecordService.searchFiles(new FileSearchRequest(query, tags, page, size));

        return response.map(p -> mapper.map(p, FileRecordDto.class));
    }
}
