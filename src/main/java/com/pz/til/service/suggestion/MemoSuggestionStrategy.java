package com.pz.til.service.suggestion;

import com.pz.til.model.Memo;
import reactor.core.publisher.Mono;


public interface MemoSuggestionStrategy {
    Mono<Memo> retrieveSuggestedMemo();
}
