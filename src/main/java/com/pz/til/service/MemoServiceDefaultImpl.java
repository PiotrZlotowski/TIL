package com.pz.til.service;

import com.pz.til.jms.producer.GenericProducer;
import com.pz.til.model.Memo;
import com.pz.til.model.MemoDTO;
import com.pz.til.repository.IMemoRepository;
import com.pz.til.service.suggestion.MemoSuggestionStrategy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MemoServiceDefaultImpl implements IMemoService {

    private IBeanConverter<MemoDTO, Memo> beanConverter;
    private IMemoRepository memoRepository;
    private MemoSuggestionStrategy memoSuggestionStrategy;
    private GenericProducer genericProducer;


    public MemoServiceDefaultImpl(IBeanConverter<MemoDTO, Memo> beanConverter, IMemoRepository memoRepository,
                                  MemoSuggestionStrategy memoSuggestionStrategy, GenericProducer genericProducer) {
        this.beanConverter = beanConverter;
        this.memoRepository = memoRepository;
        this.memoSuggestionStrategy = memoSuggestionStrategy;
        this.genericProducer = genericProducer;
    }

    @Override
    public Mono<MemoDTO> addMemo(MemoDTO memoDTO) {
        Memo memo = beanConverter.convertFromDto(memoDTO);
        Mono<MemoDTO> monoMemo = memoRepository.save(memo).doOnNext(nextMemo -> genericProducer.sendMessage("MEMO_CREATED_CHANNEL", nextMemo))
                .map(m -> beanConverter.convertFromModel(m));
        return monoMemo;
    }

    @Override
    public Flux<MemoDTO> getAllMemos() {
        Flux<Memo> allMemos = memoRepository.findAll();
        return allMemos.map(beanConverter::convertFromModel);
    }

    @Override
    public Mono<MemoDTO> retrieveSuggestedMemo() {
        Mono<Memo> suggestedMemo = memoSuggestionStrategy.retrieveSuggestedMemo();
        return suggestedMemo.map(beanConverter::convertFromModel);
    }

    @Override
    public Mono<MemoDTO> findOne(long id) {
        return memoRepository.findById(id).map(beanConverter::convertFromModel);
    }
}
