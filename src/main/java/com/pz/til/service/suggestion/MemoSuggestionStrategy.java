package com.pz.til.service.suggestion;

import com.pz.til.model.Memo;

import java.util.Optional;

public interface MemoSuggestionStrategy {
    Optional<Memo> retrieveSuggestedMemo();
}
