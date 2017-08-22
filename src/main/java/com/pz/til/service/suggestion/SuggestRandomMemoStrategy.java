package com.pz.til.service.suggestion;

import com.pz.til.model.Memo;
import com.pz.til.repository.IMemoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class SuggestRandomMemoStrategy implements MemoSuggestionStrategy {

    private IMemoRepository memoRepository;

    public SuggestRandomMemoStrategy(IMemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    @Override
    public Memo retrieveSuggestedMemo() {
        List<Memo> allMemos = memoRepository.findAll();
        Random random = new Random(System.currentTimeMillis());
        int memoRandomIndex = random.nextInt(allMemos.size());
        Memo memo = allMemos.get(memoRandomIndex);
        return memo;
    }
}
