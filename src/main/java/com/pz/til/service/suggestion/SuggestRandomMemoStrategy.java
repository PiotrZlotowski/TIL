package com.pz.til.service.suggestion;

import com.pz.til.model.Memo;
import com.pz.til.repository.IMemoRepository;
import io.vavr.control.Option;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class SuggestRandomMemoStrategy implements MemoSuggestionStrategy {

    private IMemoRepository memoRepository;
    private Random random;

    public SuggestRandomMemoStrategy(IMemoRepository memoRepository, Random random) {
        this.memoRepository = memoRepository;
        this.random = random;
    }


    @Override
    public Option<Memo> retrieveSuggestedMemo() {
        List<Memo> allMemos = memoRepository.findAll();

        if (allMemos == null || allMemos.isEmpty()) {
            return Option.none();
        }
        int memoRandomIndex = getRandomMemoIndex(allMemos);
        return Option.of(allMemos.get(memoRandomIndex));
    }

    private int getRandomMemoIndex(List<Memo> allMemos) {
        return random.nextInt(allMemos.size());
    }
}
