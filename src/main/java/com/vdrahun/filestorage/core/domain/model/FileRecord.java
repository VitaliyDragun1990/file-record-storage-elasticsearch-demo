package com.vdrahun.filestorage.core.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Data
@NoArgsConstructor
@Document(indexName = "file", createIndex = false, refreshInterval = "2s", shards = 1, replicas = 0)
public class FileRecord {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Long)
    private Long size;

    @Field(type = FieldType.Keyword)
    private Set<String> tags = new HashSet<>();

    public FileRecord(String name, Long size, Collection<String> tags) {
        this.name = name;
        this.size = size;
        this.tags.addAll(tags);
    }

    public void addTags(Collection<String> tags) {
        this.tags.addAll(tags);
    }

    public Collection<String> findNotAssignedTags(Collection<String> tagsToCheck) {
        return tagsToCheck.stream()
                .filter(tag -> !this.tags.contains(tag))
                .collect(toList());
    }

    public void removeTags(Collection<String> tags) {
        this.tags.removeAll(tags);
    }
}
