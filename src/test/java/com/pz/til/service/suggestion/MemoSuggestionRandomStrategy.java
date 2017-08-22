package com.pz.til.service.suggestion;

import static org.assertj.core.api.Assertions.*;

import com.pz.til.model.Memo;
import com.pz.til.repository.IMemoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

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
        List<Memo> memos = new ArrayList<>();
        Memo memo = new Memo(1, "Memo content", null);
        memos.add(memo);
        Mockito.when(memoRepository.findAll()).thenReturn(memos);
        Memo memo1 = memoSuggestionStrategy.retrieveSuggestedMemo();
        assertThat(memo1).isEqualTo(memo);
    }

}
