package com.pz.til.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

/**
 * Created by piotr on 11/07/2017.
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@EqualsAndHashCode
@RedisHash("Memos")
public class Memo implements Serializable{
    @Id
    private long id;
    private String content;
    private List<Tag> tags;
}
