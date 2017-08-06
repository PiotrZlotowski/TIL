package com.pz.til.service;

import com.pz.til.model.Memo;
import com.pz.til.model.MemoDTO;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

@Service
public class MemoDozerConverter implements IBeanConverter<MemoDTO, Memo> {

    private DozerBeanMapper dozerBeanMapper;

    public MemoDozerConverter(DozerBeanMapper dozerBeanMapper) {
        this.dozerBeanMapper = dozerBeanMapper;
    }

    @Override
    public Memo convertFromDto(MemoDTO dto) {
        return dozerBeanMapper.map(dto, Memo.class);
    }

    @Override
    public MemoDTO convertFromModel(Memo model) {
        return dozerBeanMapper.map(model, MemoDTO.class);
    }
}
