package com.pz.til.service.suggestion;

import com.pz.til.model.Memo;
import com.pz.til.repository.IMemoRepository;
import io.vavr.control.Option;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class MemoSuggestionRandomStrategyTests {


    private MemoSuggestionStrategy memoSuggestionStrategy;
    private IMemoRepository memoRepository;
    private Random random;

    @Before
    public void memoSuggestionInitialization() {
        memoRepository = mock(IMemoRepository.class);
        random = mock(Random.class);
        memoSuggestionStrategy = new SuggestRandomMemoStrategy(memoRepository, random);
    }


    @Test
    public void shouldReturnMockedMemoWhenRetrieveSuggestedMemoIsCalled() {
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
    public void shouldReturnEmptyOptionalWhenNoMemosAreSaved() {
        // given
        when(memoRepository.findAll()).thenReturn(null);
        // when
        Option<Memo> memo = memoSuggestionStrategy.retrieveSuggestedMemo();
        // then
        assertThat(memo).isEqualTo(Option.none());
    }
}
