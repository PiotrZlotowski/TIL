package com.pz.til.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pz.til.controller.rest.MemoRestController;
import com.pz.til.model.Memo;
import com.pz.til.model.MemoDTO;
import com.pz.til.service.IMemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MemoRestController.class)
public class MemoRestControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private IMemoService mockMemoService;

    @Test
    public void shouldReturn200WhenAddMemoIsCalled() throws Exception {
        MemoDTO memoDTO = new MemoDTO(1, "Memo content", null);
        String jsonObject = null;
        jsonObject = objectMapper.writeValueAsString(memoDTO);
        mockMvc.perform(post("/rest/addmemo").contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject)).andDo(print()).andExpect(status().is2xxSuccessful());
    }


    @Test
    public void shouldReturn200AndCollectionOfElementsWhenRetrieveMemosIsCalledWithJson() throws Exception {
       // given
        MemoDTO memo1 = new MemoDTO(1, "Memo content1", null);
        MemoDTO memo2 = new MemoDTO(2, "Memo content2", null);
        MemoDTO memo3 = new MemoDTO(3, "Memo content3", null);
        List<MemoDTO> memos = Arrays.asList(memo1, memo2, memo3);
        String mappedStringJson = objectMapper.writeValueAsString(memos);
        Mockito.when(mockMemoService.getAllMemos()).thenReturn(memos);
       // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/rest/memos")).andDo(print());
        // then
        resultActions.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().json(mappedStringJson, false)).andReturn();

    }


    @Test
    public void shouldReturn200AndCollectionOfElementsWhenRetrieveMemosIsCalledWithXml() throws Exception {
        // given
        MemoDTO memo1 = new MemoDTO(1, "Memo content1", null);
        MemoDTO memo2 = new MemoDTO(2, "Memo content2", null);
        MemoDTO memo3 = new MemoDTO(3, "Memo content3", null);
        List<MemoDTO> memos = Arrays.asList(memo1, memo2, memo3);
        Mockito.when(mockMemoService.getAllMemos()).thenReturn(memos);
        String expectedResponse = "<List xmlns=\"\"><item><id>1</id><memoContent>Memo content1</memoContent></item><item><id>2</id><memoContent>Memo content2</memoContent></item><item><id>3</id><memoContent>Memo content3</memoContent></item></List>";
        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/rest/memos").accept(MediaType.APPLICATION_XML)).andDo(print());
        // then
        resultActions.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().xml(expectedResponse)).andReturn();

    }

}
