package com.pz.til.service;

import com.pz.til.model.MemoDTO;

import java.util.List;


public interface IMemoService {

    MemoDTO addMemo(MemoDTO memoDTO);
    List<MemoDTO> getAllMemos();
    MemoDTO retrieveSuggestedMemo();
}
