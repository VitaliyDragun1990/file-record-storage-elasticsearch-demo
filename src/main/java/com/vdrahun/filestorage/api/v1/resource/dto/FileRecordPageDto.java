package com.vdrahun.filestorage.api.v1.resource.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class FileRecordPageDto {

    private Integer total;

    private List<FileRecordDto> page;
}
