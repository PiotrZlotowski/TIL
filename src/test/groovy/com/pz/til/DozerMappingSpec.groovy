package com.pz.til

import com.pz.til.configuration.DozerConfig
import com.pz.til.model.Memo
import com.pz.til.model.MemoDTO
import org.dozer.DozerBeanMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = DozerConfig.class)
class DozerMappingSpec extends Specification {

    @Autowired
    DozerBeanMapper dozerBeanMapper

    def "Should return one dozer mapping file"() {
        when:
        List<String> mappingFiles = dozerBeanMapper.getMappingFiles();
        then:
        mappingFiles.size() == 1
    }

    def "Should convert given dto to normal model class"() {
        given:
        MemoDTO memoDTO = new MemoDTO();
        memoDTO.id = givenId
        memoDTO.memoContent = givenMemo
        when:
        Memo memo = dozerBeanMapper.map(memoDTO, Memo.class)
        then:
        memo.id == givenId
        memo.content == givenMemo
        where:
        givenId || givenMemo
        1       || "Memo content 1"
        2       || "Memo content 2"
    }

    def "Should convert given model to dto class"() {
        given:
        Memo memo = new Memo()
        memo.id = givenId
        memo.content = givenMemo
        when:
        MemoDTO memoDTO = dozerBeanMapper.map(memo, MemoDTO.class)
        then:
        memoDTO.id == givenId
        memoDTO.memoContent == givenMemo
        where:
        givenId || givenMemo
        1       || "Memo content 1"
        2       || "Memo content 2"
    }






}
