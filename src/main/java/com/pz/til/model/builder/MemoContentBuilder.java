package com.pz.til.model.builder;

public interface MemoContentBuilder {

    MemoOptionalFieldsBuilder<?> withContent(String content);

}
