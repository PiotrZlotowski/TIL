package com.pz.til.controller.rest;

import com.pz.til.model.MemoDTO;
import com.pz.til.service.IMemoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * Created by piotr on 09/07/2017.
 */
@RestController()
@RequestMapping("/rest")
public class MemoRestController {

    private IMemoService memoService;


    public MemoRestController(IMemoService memoService) {
        this.memoService = memoService;
    }

    @PostMapping("/addmemo")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Mono<MemoDTO> addMemo(@Valid @RequestBody MemoDTO memoDto) {
        return memoService.addMemo(memoDto);

    }

    @GetMapping(value = "/memos", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Flux<MemoDTO> retrieveMemos() {
         return memoService.getAllMemos();
    }


    @GetMapping("/memo/{id}")
    public Mono<ResponseEntity<MemoDTO>> retrieveSelectedMemo(@PathVariable("id") long id) {
        return memoService.findOne(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @GetMapping("/SuggestedMemo")
    public Mono<ResponseEntity<MemoDTO>> suggestedMemo() {
        return memoService.retrieveSuggestedMemo()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
