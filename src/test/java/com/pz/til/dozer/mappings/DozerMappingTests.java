package com.pz.til.dozer.mappings;

import static org.assertj.core.api.Assertions.*;

import com.pz.til.configuration.DozerConfig;
import com.pz.til.model.Memo;
import com.pz.til.model.MemoDTO;
import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by piotr on 17/07/2017.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DozerConfig.class)
class DozerMappingTests {

    private DozerBeanMapper dozerBeanMapper;

    @Autowired
    DozerMappingTests(DozerBeanMapper dozerBeanMapper) {
        this.dozerBeanMapper = dozerBeanMapper;
    }

    @Test
    void whenTheBeanCreatedShouldHaveOneMappingFile() {
        // when
        List<String> mappingFiles = dozerBeanMapper.getMappingFiles();
        // then
        assertThat(mappingFiles).hasSize(1);
    }

    @ParameterizedTest
    @MethodSource("memoDtoArguments")
    void whenDtoClassIsGivenShouldBeConvertedToDto(int id, String memoContent) {
        // given
        MemoDTO memoDTO = new MemoDTO();
        memoDTO.setId(id);
        memoDTO.setMemoContent(memoContent);
        // when
        Memo memo = dozerBeanMapper.map(memoDTO, Memo.class);
        // then
        assertThat(memo.getContent()).isEqualTo(memoDTO.getMemoContent());
        assertThat(memo.getId()).isEqualTo(memoDTO.getId());
        assertThat(memo.getTags()).isEqualTo(memoDTO.getTags());
    }

    @ParameterizedTest
    @CsvSource({"1, Memo content", "2, Memo content 2", "3, Memo content 3"})
    void whenModelClassIsGivenShouldBeConvertedToDto(long id, String memoContent) {
        // given
        Memo memo = new Memo();
        memo.setId(id);
        memo.setContent(memoContent);
        // when
        MemoDTO memoDTO = dozerBeanMapper.map(memo, MemoDTO.class);
        // then
        assertThat(memoDTO.getId()).isEqualTo(memo.getId()).isEqualTo(id);
        assertThat(memoDTO.getMemoContent()).isEqualTo(memo.getContent()).isEqualTo(memoContent);
    }
    static Stream<Arguments> memoDtoArguments() {
        return Stream.of(Arguments.of(1, "Memo content 1"), Arguments.of(2, "Memo content 2"));
    }
}
