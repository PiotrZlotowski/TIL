package com.pz.til.model.builder;

import com.pz.til.model.Memo;

public interface MemoContentBuilder {

    MemoOptionalFieldsBuilder<Memo> withContent(String content);

}
