package com.pz.til.dozer.mappings;

import com.pz.til.model.Memo;
import com.pz.til.model.MemoDTO;
import org.assertj.core.api.ObjectArrayAssert;
import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Created by piotr on 17/07/2017.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
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
        /*
            static method cannot be used with inner classes and that's the reason I decided to go for @ArgumentsSource
            instead of @MethodSource which was improved in Junit 5.1 and now doesn't require
         */
        @ArgumentsSource(CustomArgumentProvider.class)
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

    static class CustomArgumentProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(Arguments.of(1, "Memo content"), Arguments.of(2, "Memo content 2"), Arguments.of(3, "Memo content 3"));
        }
    }
}
