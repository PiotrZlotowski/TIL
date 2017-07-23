package com.pz.til.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by piotr on 16/07/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemoDTO {
    private long id;
    private String memoContent;
    private List<Tag> tags;
}
