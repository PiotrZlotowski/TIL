package com.pz.til.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by piotr on 16/07/2017.
 */
@AllArgsConstructor
@EqualsAndHashCode
@Data
public class Tag implements Serializable {
    private String tagName;
}
