package com.pz.til.dozer.mappings;

import com.pz.til.configuration.DozerConfig;
import com.pz.til.model.Memo;
import com.pz.til.model.MemoDTO;
import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Created by piotr on 17/07/2017.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DozerConfig.class)
@DisplayName("Dozer Spring Bean Test ")
class DozerMappingTests {


    private static final String PARAMETERIZED_TEST_MESSAGE ="Current iteration: {index} with the following arguments: {arguments})";

    private DozerBeanMapper dozerBeanMapper;

    @Autowired
    DozerMappingTests(DozerBeanMapper dozerBeanMapper) {
        this.dozerBeanMapper = dozerBeanMapper;
    }

    @Test
    @DisplayName("is initialized with one mapping file")
    void whenTheBeanCreatedShouldHaveOneMappingFile() {
        // when
        List<String> mappingFiles = dozerBeanMapper.getMappingFiles();
        // then
        assertThat(mappingFiles).hasSize(1);
    }

    @Nested
    @DisplayName("and based on that is able to")
    class MappingTests {
        @ParameterizedTest(name = PARAMETERIZED_TEST_MESSAGE)
        @CsvSource({"1, Memo content", "2, Memo content 2", "3, Memo content 3"})
        @DisplayName("map DTO class to model class")
        void whenDtoClassIsGivenShouldBeConvertedToDto(int id, String memoContent) {
            assumeTrue(!dozerBeanMapper.getMappingFiles().isEmpty(), () -> "Dozer Bean Mapper is empty");
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


        @ParameterizedTest(name = PARAMETERIZED_TEST_MESSAGE)
        @CsvSource({"1, Memo content", "2, Memo content 2", "3, Memo content 3"})
        @DisplayName("map model class to DTO class")
        void whenModelClassIsGivenShouldBeConvertedToDto(long id, String memoContent) {
            assumeTrue(!dozerBeanMapper.getMappingFiles().isEmpty(), () -> "Dozer Bean Mapper is empty");
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
    }
}
