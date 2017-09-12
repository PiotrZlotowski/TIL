package com.pz.til.service;

import com.pz.til.model.Memo;
import com.pz.til.model.MemoDTO;
import com.pz.til.repository.IMemoRepository;
import com.pz.til.service.suggestion.MemoSuggestionStrategy;
import io.vavr.control.Option;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MemoServiceDefaultImpl implements IMemoService {

    private IBeanConverter<MemoDTO, Memo> beanConverter;
    private IMemoRepository memoRepository;
    private MemoSuggestionStrategy memoSuggestionStrategy;


    public MemoServiceDefaultImpl(IBeanConverter<MemoDTO, Memo> beanConverter, IMemoRepository memoRepository, MemoSuggestionStrategy memoSuggestionStrategy) {
        this.beanConverter = beanConverter;
        this.memoRepository = memoRepository;
        this.memoSuggestionStrategy = memoSuggestionStrategy;
    }

    @Override
    public MemoDTO addMemo(MemoDTO memoDTO) {
        Memo memo = beanConverter.convertFromDto(memoDTO);
        Memo savedMemo = memoRepository.save(memo);
        return beanConverter.convertFromModel(savedMemo);
    }

    @Override
    public List<MemoDTO> getAllMemos() {
        return null;
    }

    @Override
    public MemoDTO retrieveSuggestedMemo() {
        Option<Memo> memoOptional = memoSuggestionStrategy.retrieveSuggestedMemo();
        MemoDTO memoDTO = memoOptional.map(beanConverter::convertFromModel).getOrElseThrow(NoSuchElementException::new);
        return memoDTO;
    }
}
