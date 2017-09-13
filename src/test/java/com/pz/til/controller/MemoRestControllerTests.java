package com.pz.til.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pz.til.configuration.InternationalizationConfig;
import com.pz.til.controller.rest.MemoRestController;
import com.pz.til.model.MemoDTO;
import com.pz.til.service.IMemoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = MemoRestController.class, includeFilters = {@Filter(type = FilterType.ASSIGNABLE_TYPE, classes = InternationalizationConfig.class)})
class MemoRestControllerTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @MockBean
    private IMemoService mockMemoService;


    @Autowired
    MemoRestControllerTests(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }


    @Test
    void shouldReturn200WhenAddMemoIsCalled() throws Exception {
        MemoDTO toSaveMemo = new MemoDTO(0, "Memo content", null);
        MemoDTO savedMemo = new MemoDTO(1, "Memo content", null);
        when(mockMemoService.addMemo(toSaveMemo)).thenReturn(savedMemo);
        String toSaveMemoJsonObject = objectMapper.writeValueAsString(toSaveMemo);
        String savedMemoJsonObject = objectMapper.writeValueAsString(savedMemo);
        mockMvc.perform(post("/rest/addmemo").contentType(MediaType.APPLICATION_JSON)
                .content(toSaveMemoJsonObject)).andDo(print()).andExpect(status().is2xxSuccessful()).andExpect(content().json(savedMemoJsonObject));
    }

    @Test
    void shouldReturn400BadRequestWhenNullRequestIsProvided() throws Exception {
        MemoDTO memoDTO = new MemoDTO(1L, null, null);
        String jsonObject = objectMapper.writeValueAsString(memoDTO);
        mockMvc.perform(post("/rest/addmemo").contentType(MediaType.APPLICATION_JSON).locale(Locale.UK)
                .content(jsonObject)).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400BadRequestWhenEmptyMemoContentIsProvided() throws Exception {
        MemoDTO memoDTO = new MemoDTO(1L, "", null);
        String jsonObject = objectMapper.writeValueAsString(memoDTO);
        mockMvc.perform(post("/rest/addmemo").contentType(MediaType.APPLICATION_JSON).locale(Locale.UK)
                .content(jsonObject)).andDo(print()).andExpect(status().isBadRequest());
    }


    @Test
    void shouldReturn200AndCollectionOfElementsWhenRetrieveMemosIsCalledWithJson() throws Exception {
       // given
        MemoDTO memo1 = new MemoDTO(1, "Memo content1", null);
        MemoDTO memo2 = new MemoDTO(2, "Memo content2", null);
        MemoDTO memo3 = new MemoDTO(3, "Memo content3", null);
        List<MemoDTO> memos = Arrays.asList(memo1, memo2, memo3);
        String mappedStringJson = objectMapper.writeValueAsString(memos);
        when(mockMemoService.getAllMemos()).thenReturn(memos);
       // when
        ResultActions resultActions = mockMvc.perform(get("/rest/memos")).andDo(print());
        // then
        resultActions.andExpect(status().is2xxSuccessful())
                .andExpect(content().json(mappedStringJson, false)).andReturn();

    }


    @Test
    void shouldReturn200AndCollectionOfElementsWhenRetrieveMemosIsCalledWithXml() throws Exception {
        // given
        MemoDTO memo1 = new MemoDTO(1, "Memo content1", null);
        MemoDTO memo2 = new MemoDTO(2, "Memo content2", null);
        MemoDTO memo3 = new MemoDTO(3, "Memo content3", null);
        List<MemoDTO> memos = Arrays.asList(memo1, memo2, memo3);
        when(mockMemoService.getAllMemos()).thenReturn(memos);
        String expectedResponse = "<List xmlns=\"\"><item><id>1</id><memoContent>Memo content1</memoContent></item><item><id>2</id><memoContent>Memo content2</memoContent></item><item><id>3</id><memoContent>Memo content3</memoContent></item></List>";
        // when
        ResultActions resultActions = mockMvc.perform(get("/rest/memos").accept(MediaType.APPLICATION_XML)).andDo(print());
        // then
        resultActions.andExpect(status().is2xxSuccessful())
                .andExpect(content().xml(expectedResponse)).andReturn();

    }

    @Test
    void shouldReturn200AndOneRecommendedMemoWhenSuggestedMemoIsCalledWithJson() throws Exception {
        // given
        MemoDTO memoDTO = new MemoDTO(1, "Suggested Memo Content", null);
        when(mockMemoService.retrieveSuggestedMemo()).thenReturn(memoDTO);
        String memoDTOAsJson = objectMapper.writeValueAsString(memoDTO);
        // when
        ResultActions resultActions = mockMvc.perform(get("/rest/SuggestedMemo").accept(MediaType.APPLICATION_JSON_VALUE)).andDo(print());
        // then
        resultActions.andExpect(status().is2xxSuccessful()).andExpect(content().json(memoDTOAsJson));
    }

}
