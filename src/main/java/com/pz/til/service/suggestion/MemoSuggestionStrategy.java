package com.pz.til.service.suggestion;

import com.pz.til.model.Memo;
import io.vavr.control.Option;

import java.util.Optional;

public interface MemoSuggestionStrategy {
    Option<Memo> retrieveSuggestedMemo();
}
