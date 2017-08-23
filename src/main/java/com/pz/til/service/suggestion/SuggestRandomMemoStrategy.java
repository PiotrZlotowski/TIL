package com.pz.til.service.suggestion;

import com.pz.til.model.Memo;
import com.pz.til.repository.IMemoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class SuggestRandomMemoStrategy implements MemoSuggestionStrategy {

    private IMemoRepository memoRepository;

    public SuggestRandomMemoStrategy(IMemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    @Override
    public Optional<Memo> retrieveSuggestedMemo() {
        List<Memo> allMemos = memoRepository.findAll();

        if (allMemos == null || allMemos.isEmpty()) {
            return Optional.empty();
        }
        int memoRandomIndex = getRandomMemoIndex(allMemos);
        Optional<Memo> optionalMemo = Optional.ofNullable(allMemos.get(memoRandomIndex));
        return optionalMemo;
    }

    private int getRandomMemoIndex(List<Memo> allMemos) {
        Random random = new Random(System.currentTimeMillis());
        return random.nextInt(allMemos.size());
    }
}
