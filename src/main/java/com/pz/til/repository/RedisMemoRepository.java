package com.pz.til.repository;

import com.pz.til.model.Memo;
import lombok.extern.java.Log;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Map;

@Repository
@Log
public class RedisMemoRepository implements IMemoRepository {

    private static final String MEMO_KEY = "MEMO";
    private ReactiveRedisTemplate reactiveRedisTemplate;
    private ReactiveHashOperations<String, Long, Memo> reactiveHashOperations;

    public RedisMemoRepository(ReactiveRedisTemplate reactiveRedisTemplate) {
        this.reactiveRedisTemplate = reactiveRedisTemplate;
    }

    @PostConstruct
    public void init() {
        reactiveHashOperations = reactiveRedisTemplate.opsForHash();
    }

    @Override
    public Mono<Memo> save(Memo memo) {
        findAll().count().subscribe(element -> log.info(String.valueOf(element)));
        Mono<Memo> memoMono = Mono.just(memo).zipWith(findAll().count(), (memoToBeInserted, count) -> {
            log.info(String.valueOf(count + 1));
            memoToBeInserted.setId(count + 1);
            return memoToBeInserted;
        });
        memoMono.flatMap(memo1 -> reactiveHashOperations.put(MEMO_KEY, memo.getId(), memo1)).subscribe();
        return memoMono;
    }

    @Override
    public Flux<Memo> findAll() {
        return reactiveHashOperations.entries(MEMO_KEY).map(Map.Entry::getValue);
    }

    @Override
    public Mono<Memo> findById(long id) {
        return reactiveHashOperations.get(MEMO_KEY, id);
    }
}
