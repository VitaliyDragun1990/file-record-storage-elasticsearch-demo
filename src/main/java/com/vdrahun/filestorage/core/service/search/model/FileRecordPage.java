package com.vdrahun.filestorage.core.service.search.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class FileRecordPage<T> {

    private int total;

    private final List<T> page = new ArrayList<>();

    public FileRecordPage(int total, List<T> page) {
        this.total = total;
        this.page.addAll(page);
    }

    public <U> FileRecordPage<U> map(Function<? super T, ? extends U> var1) {
        return new FileRecordPage<>(this.total, this.page.stream().map(var1).collect(toList()));
    }
}
