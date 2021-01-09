package com.vdrahun.filestorage.core.service.tag;

import java.util.List;

public interface TagProvider {

    List<String> provideTagsBy(String filename);
}
