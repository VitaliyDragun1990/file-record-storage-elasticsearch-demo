package com.vdrahun.filestorage.api.v1.resource.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
public class FileRecordDto {

    private String id;

    private String name;

    private Long size;

    private List<String> tags;

}
