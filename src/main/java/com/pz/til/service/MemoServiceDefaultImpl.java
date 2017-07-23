package com.pz.til.service;

import com.pz.til.model.Memo;
import com.pz.til.model.MemoDTO;
import com.pz.til.repository.IMemoRepository;

import java.util.List;

public class MemoServiceDefaultImpl implements IMemoService {

    private IBeanConverter<MemoDTO, Memo> beanConverter;
    private IMemoRepository memoRepository;


    public MemoServiceDefaultImpl(IBeanConverter<MemoDTO, Memo> beanConverter, IMemoRepository memoRepository) {
        this.beanConverter = beanConverter;
        this.memoRepository = memoRepository;
    }

    @Override
    public void addMemo(MemoDTO memoDTO) {

    }

    @Override
    public List<MemoDTO> getAllMemos() {
        return null;
    }
}
