package com.pz.til.service;

import com.pz.til.model.MemoDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IMemoService {

    void addMemo(MemoDTO memoDTO);
    List<MemoDTO> getAllMemos();
}
