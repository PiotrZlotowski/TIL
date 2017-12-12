package com.pz.til.model.builder;

import com.pz.til.model.Tag;

import java.util.List;

public interface MemoOptionalFieldsBuilder<T> {
    MemoOptionalFieldsBuilder withTags(List<Tag> tags);
    T build();
}
