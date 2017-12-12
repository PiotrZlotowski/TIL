package com.pz.til.model;

import com.pz.til.model.builder.MemoContentBuilder;
import com.pz.til.model.builder.MemoOptionalFieldsBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by piotr on 16/07/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class MemoDTO implements Serializable {

    private long id;
    @NotNull
    @NotEmpty
    private String memoContent;
    private List<Tag> tags;


    public static class MemoDtoBuilder implements MemoContentBuilder, MemoOptionalFieldsBuilder<MemoDTO> {

        List<Consumer<MemoDTO>> operations;

        private MemoDtoBuilder() {
            operations = new ArrayList<>();
        }

        public static MemoContentBuilder memo() {
            return new MemoDtoBuilder();
        }

        @Override
        public MemoOptionalFieldsBuilder withContent(String content) {
            operations.add(c -> c.memoContent = content);
            return this;
        }

        @Override
        public MemoOptionalFieldsBuilder withTags(List<Tag> tags) {
            operations.add(c -> c.tags = tags);
            return this;
        }

        @Override
        public MemoDTO build() {
            MemoDTO memo = new MemoDTO();
            operations.forEach(c -> c.accept(memo));
            validate(memo);
            return memo;
        }

        private void validate(MemoDTO memo) {
            if (memo.memoContent == null || memo.memoContent.isEmpty()) {
                throw new IllegalStateException("Memo object is in the illegal state");
            }
        }
    }
}
