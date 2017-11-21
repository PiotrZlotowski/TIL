package com.pz.til.service.suggestion;

import com.pz.til.model.Memo;
import io.vavr.control.Option;


public interface MemoSuggestionStrategy {
    Option<Memo> retrieveSuggestedMemo();
}
