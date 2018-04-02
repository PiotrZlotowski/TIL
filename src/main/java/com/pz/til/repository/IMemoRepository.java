package com.pz.til.repository;

import com.pz.til.model.Memo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by piotr on 12/07/2017.
 */
public interface IMemoRepository {
    Mono<Memo> save(Memo memo);

    Flux<Memo> findAll();

    Mono<Memo> findById(long id);
}
