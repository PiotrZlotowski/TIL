package com.pz.til.dozer.mappings;

import static org.assertj.core.api.Assertions.*;

import com.pz.til.configuration.DozerConfig;
import com.pz.til.model.Memo;
import com.pz.til.model.MemoDTO;
import org.dozer.DozerBeanMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by piotr on 17/07/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DozerConfig.class)
public class DozerMappingTests {

    public static final String MEMO_CONTENT = "Memo content";
    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Test
    public void whenTheBeanCreatedShouldHaveOneMappingFile() {
        // when
        List<String> mappingFiles = dozerBeanMapper.getMappingFiles();
        // then
        assertThat(mappingFiles).hasSize(1);
    }

    @Test
    public void whenDtoClassIsGivenShouldBeConvertedToDto() {
        // given
        MemoDTO memoDTO = new MemoDTO();
        memoDTO.setId(1L);
        memoDTO.setMemoContent(MEMO_CONTENT);
        // when
        Memo memo = dozerBeanMapper.map(memoDTO, Memo.class);
        // then
        assertThat(memo.getContent()).isEqualTo(memoDTO.getMemoContent());
        assertThat(memo.getId()).isEqualTo(memoDTO.getId());
        assertThat(memo.getTags()).isEqualTo(memoDTO.getTags());
    }


    @Test
    public void whenModelClassIsGivenShouldBeConvetedToDto() {
        // given
        Memo memo = new Memo();
        memo.setId(1L);
        memo.setContent(MEMO_CONTENT);
        // when
        MemoDTO memoDTO = dozerBeanMapper.map(memo, MemoDTO.class);
        // then
        assertThat(memoDTO.getId()).isEqualTo(memo.getId()).isEqualTo(1);
        assertThat(memoDTO.getMemoContent()).isEqualTo(memo.getContent()).isEqualTo(MEMO_CONTENT);
    }



}
