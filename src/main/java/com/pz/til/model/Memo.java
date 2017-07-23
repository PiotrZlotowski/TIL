package com.pz.til.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

/**
 * Created by piotr on 11/07/2017.
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@RedisHash("Memos")
public class Memo {
    @Id
    private long id;
    private String content;
    private List<Tag> tags;
}
