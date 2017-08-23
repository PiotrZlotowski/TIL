package com.pz.til.service.suggestion;

import static org.assertj.core.api.Assertions.*;

import com.pz.til.model.Memo;
import com.pz.til.model.MemoDTO;
import com.pz.til.repository.IMemoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class MemoSuggestionRandomStrategy {


    private MemoSuggestionStrategy memoSuggestionStrategy;
    private IMemoRepository memoRepository;

    @Before
    public void memoSuggestionInitialization() {
        memoRepository = mock(IMemoRepository.class);
        memoSuggestionStrategy = new SuggestRandomMemoStrategy(memoRepository);
    }




    @Test
    public void shouldReturnMockedMemoWhenRetrieveSuggestedMemoIsCalled() {
        // given
        List<Memo> memos = new ArrayList<>();
        Memo memo = new Memo(1, "Memo content", null);
        memos.add(memo);
        when(memoRepository.findAll()).thenReturn(memos);
        // when
        Optional<Memo> memoOptional = memoSuggestionStrategy.retrieveSuggestedMemo();
        Memo memoFromOptional = memoOptional.orElseThrow(NoSuchElementException::new);
        // then
        assertThat(memoFromOptional).isEqualTo(memo);
    }

    @Test
    public void shouldReturnEmptyOptionalWhenNoMemosAreSaved() {
        // given
        when(memoRepository.findAll()).thenReturn(null);
        // when
        Optional<Memo> memo = memoSuggestionStrategy.retrieveSuggestedMemo();
        // then
        assertThat(memo).isEqualTo(Optional.empty());
    }

}
