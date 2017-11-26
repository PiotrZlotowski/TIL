package com.pz.til.service.suggestion;

import com.pz.til.model.Memo;
import com.pz.til.repository.IMemoRepository;
import io.vavr.control.Option;
import junit.extension.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

//@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
class MemoSuggestionRandomStrategyTests {


    private MemoSuggestionStrategy memoSuggestionStrategy;
//    private IMemoRepository memoRepository;

    @BeforeEach
    void memoSuggestionInitialization(@Mock IMemoRepository memoRepository, @Mock Random random) {
        memoSuggestionStrategy = new SuggestRandomMemoStrategy(memoRepository, random);
    }


    @Test
    void shouldReturnMockedMemoWhenRetrieveSuggestedMemoIsCalled(@Mock IMemoRepository memoRepository, @Mock Random random) {
        // given
        List<Memo> memos = new ArrayList<>();
        Memo memo = new Memo(1, "Memo content", null);
        memos.add(memo);
        when(memoRepository.findAll()).thenReturn(memos);
        when(random.nextInt(memos.size())).thenReturn(0);
        // when
        Option<Memo> memoOptional = memoSuggestionStrategy.retrieveSuggestedMemo();
        Memo memoFromOptional = memoOptional.getOrElseThrow(NoSuchElementException::new);
        // then
        assertThat(memoFromOptional).isEqualTo(memo);
    }

    @Test
    void shouldReturnEmptyOptionalWhenNullIsReturned(@Mock IMemoRepository memoRepository) {
        // given
        when(memoRepository.findAll()).thenReturn(null);
        // when
        Option<Memo> memo = memoSuggestionStrategy.retrieveSuggestedMemo();
        // then
        assertThat(memo).isEqualTo(Option.none());
    }

    @Test
    void shouldReturnEmptyOptionalWhenResultsAreNotInDatabase(@Mock IMemoRepository memoRepository) {
        // given
        when(memoRepository.findAll()).thenReturn(Collections.emptyList());
        // when
        Option<Memo> memo = memoSuggestionStrategy.retrieveSuggestedMemo();
        // then
        assertThat(memo).isEqualTo(Option.none());
    }
}
