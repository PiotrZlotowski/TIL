package com.pz.til.model;

import com.pz.til.model.builder.MemoContentBuilder;
import com.pz.til.model.builder.MemoOptionalFieldsBuilder;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by piotr on 11/07/2017.
 */
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@RedisHash("Memos")
public class Memo implements Serializable {
    @Id
    private long id;
    private String content;
    private List<Tag> tags;

    public Memo(String id, String content, List<Tag> tags) {
        this.id = Long.valueOf(id);
        this.content = content;
        this.tags = tags;
    }

    public static class MemoBuilder implements MemoContentBuilder, MemoOptionalFieldsBuilder<Memo> {

        List<Consumer<Memo>> operations;

        private MemoBuilder() {
            operations = new ArrayList<>();
        }

        public static MemoContentBuilder memo() {
            return new MemoBuilder();
        }

        @Override
        public MemoOptionalFieldsBuilder<Memo> withContent(String content) {
            operations.add(c -> c.content = content);
            return this;
        }

        @Override
        public MemoOptionalFieldsBuilder withTags(List<Tag> tags) {
            operations.add(c -> c.tags =  tags);
            return this;
        }

        @Override
        public Memo build() {
            Memo memo = new Memo();
            operations.forEach(c -> c.accept(memo));
            validate(memo);
            return memo;
        }

        private void validate(Memo memo) {
            if (memo.content == null || memo.content.isEmpty()) {
                throw new IllegalStateException("Memo object is in the illegal state");
            }
        }
    }


}
