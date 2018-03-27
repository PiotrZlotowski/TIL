package com.pz.til.repository;

import com.pz.til.model.Memo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Created by piotr on 12/07/2017.
 */
public interface IMemoRepository {
    String MEMO_KEY = "MEMO";

    Mono<Memo> save(Memo memo);

    Flux<Memo> findAll();

    Mono<Memo> findById(long id);
}
