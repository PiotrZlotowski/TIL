package com.pz.til.controller.rest;

import com.pz.til.model.Memo;
import com.pz.til.model.MemoDTO;
import com.pz.til.repository.IMemoRepository;
import com.pz.til.service.IMemoService;
import org.dozer.DozerBeanMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void addMemo(MemoDTO memoDto) {

    }


    @GetMapping("/memos")
    public List<MemoDTO> retrieveMemos() {
         return memoService.getAllMemos();
    }


    @GetMapping("/memo/{id}")
    public MemoDTO retrieveSelectedMemo(@PathVariable("id") long id) {
        return null;
    }


    @GetMapping
    public MemoDTO suggestedMemo() {
        return null;
    }

}
