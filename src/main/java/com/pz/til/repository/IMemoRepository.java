package com.pz.til.repository;

import com.pz.til.model.Memo;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by piotr on 12/07/2017.
 */
public interface IMemoRepository extends CrudRepository<Memo, Long> {

}
