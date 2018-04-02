package com.pz.til.service.suggestion;

import com.pz.til.model.Memo;
import com.pz.til.repository.IMemoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<Memo> retrieveSuggestedMemo() {
        Flux<Memo> allMemos = memoRepository.findAll();

        return allMemos.collectList().map(memoList -> memoList.get(getRandomMemoIndex(memoList)));
    }

    private int getRandomMemoIndex(List<Memo> allMemos) {
        return random.nextInt(allMemos.size());
    }
}
