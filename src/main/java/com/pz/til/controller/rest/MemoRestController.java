package com.pz.til.controller.rest;

import com.pz.til.model.MemoDTO;
import com.pz.til.service.IMemoService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

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
    public MemoDTO addMemo(@Valid @RequestBody MemoDTO memoDto) {
        return memoService.addMemo(memoDto);

    }

    @GetMapping(value = "/memos", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<MemoDTO> retrieveMemos() {
         return memoService.getAllMemos();
    }


    @GetMapping("/memo/{id}")
    public MemoDTO retrieveSelectedMemo(@PathVariable("id") long id) {
        return null;
    }


    @GetMapping("/SuggestedMemo")
    public MemoDTO suggestedMemo() {
        return memoService.retrieveSuggestedMemo();
    }

}
