package com.pz.til.service;

import com.pz.til.model.MemoDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface IMemoService {

    Mono<MemoDTO> addMemo(MemoDTO memoDTO);
    Flux<MemoDTO> getAllMemos();
    Mono<MemoDTO> retrieveSuggestedMemo();
    Mono<MemoDTO> findOne(long id);
}
