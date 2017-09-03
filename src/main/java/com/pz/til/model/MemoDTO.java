package com.pz.til.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by piotr on 16/07/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class MemoDTO {

    private long id;
    @NotNull
    private String memoContent;
    private List<Tag> tags;
}
