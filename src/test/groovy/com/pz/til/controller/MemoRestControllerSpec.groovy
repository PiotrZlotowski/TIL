package com.pz.til.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.pz.til.model.MemoDTO
import com.pz.til.service.IMemoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
@ContextConfiguration
class MemoRestControllerSpec extends Specification {

    @Autowired
    IMemoService memoService

    @Autowired
    MockMvc mockMvc

    @Autowired
    ObjectMapper objectMapper


    def "Rest endpoint should return 200 when called" () {
        given:
        def memoDTO = new MemoDTO(1, "Memo content", null)
        def jsonObject = objectMapper.writeValueAsString(memoDTO)
        when:
        def mockMvcOperation = mockMvc.perform(post("/rest/addmemo").contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject)).andDo(print())
        then:
        mockMvcOperation.andExpect(status().is2xxSuccessful())
    }

    def "Rest endpoint should return 200 and collection of element when retrieve memo method is called"() {
        given: "Memo DTOs"
        def memo1 = new MemoDTO(1, "Memo content1", null)
        def memo2 = new MemoDTO(2, "Memo content2", null)
        def memo3 = new MemoDTO(3, "Memo content3", null)
        def memos = Arrays.asList(memo1, memo2, memo3)
        and: "Mapped array as Json String"
        def mappedStringJson = objectMapper.writeValueAsString(memos)
        and: "Stubbed memo service"
        memoService.getAllMemos() >> memos
        when: "Get request is performed"
        def resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/rest/memos")).andDo(print())
        then:
        resultActions.andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().json(mappedStringJson, false)).andReturn();

    }

    /**
     * Workaround used to replace @MockBean solution in normal Java / Spring Boot slices testing
     * Another possible workaround https://blog.pchudzik.com/201707/springmock-v1/
     */
    @TestConfiguration
//    @ContextConfiguration
    static class SpockMockConfig {
        def detachedMockFactory = new DetachedMockFactory()
        @Bean
        IMemoService memoRepository() {
            detachedMockFactory.Stub(IMemoService, name: "memoService")
        }
    }

}
