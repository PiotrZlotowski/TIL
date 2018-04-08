package com.pz.til.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pz.til.configuration.InternationalizationConfig;
import com.pz.til.controller.rest.MemoRestController;
import com.pz.til.model.MemoDTO;
import com.pz.til.service.IMemoService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(value = MemoRestController.class, includeFilters = {@Filter(type = FilterType.ASSIGNABLE_TYPE, classes = InternationalizationConfig.class)})
@DisabledOnOs(OS.MAC)
class MemoRestControllerTests {

    private WebTestClient webTestClient;
    private ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    private IMemoService mockMemoService;


    @Autowired
    MemoRestControllerTests(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }


    @Test
    void shouldReturn200WhenAddMemoIsCalled() throws Exception {
        MemoDTO toSaveMemo = new MemoDTO(0, "Memo content", null);
        MemoDTO savedMemo = new MemoDTO(1, "Memo content", null);
        when(mockMemoService.addMemo(toSaveMemo)).thenReturn(Mono.just(savedMemo));
        webTestClient.post().uri("/rest/addmemo").body(BodyInserters.fromObject(toSaveMemo)).exchange().expectStatus()
                .isCreated().expectBody(MemoDTO.class).isEqualTo(savedMemo);
        verify(mockMemoService).addMemo(toSaveMemo);
    }

    @Test
    void shouldReturn400BadRequestWhenNullRequestIsProvided() throws Exception {
        MemoDTO memoDTO = new MemoDTO(1L, null, null);
        String jsonObject = objectMapper.writeValueAsString(memoDTO);
        webTestClient.post().uri("/rest/addmemo").body(BodyInserters.fromObject(memoDTO)).exchange().expectStatus().isBadRequest();
    }

    @Test
    void shouldReturn400BadRequestWhenEmptyMemoContentIsProvided() throws Exception {
        MemoDTO memoDTO = new MemoDTO(1L, "", null);
        String jsonObject = objectMapper.writeValueAsString(memoDTO);
        webTestClient.post().uri("/rest/addmemo").body(BodyInserters.fromObject(memoDTO)).exchange().expectStatus().isBadRequest();
    }


    @Test
    void shouldReturn200AndCollectionOfElementsWhenRetrieveMemosIsCalledWithJson() throws Exception {
       // given
        MemoDTO memo1 = new MemoDTO(1, "Memo content1", null);
        MemoDTO memo2 = new MemoDTO(2, "Memo content2", null);
        MemoDTO memo3 = new MemoDTO(3, "Memo content3", null);
        List<MemoDTO> memos = Arrays.asList(memo1, memo2, memo3);
        when(mockMemoService.getAllMemos()).thenReturn(Flux.fromIterable(memos));
       // when
        WebTestClient.ResponseSpec exchange = webTestClient.get().uri("/rest/memos").accept(MediaType.APPLICATION_JSON).exchange();
        // then
        exchange.expectStatus().isOk().expectBodyList(MemoDTO.class).hasSize(3).contains(memo1, memo2, memo3);
        verify(mockMemoService).getAllMemos();

    }


    @Test
    @Disabled
    void shouldReturn200AndCollectionOfElementsWhenRetrieveMemosIsCalledWithXml() throws Exception {
        // given
        MemoDTO memo1 = new MemoDTO(1, "Memo content1", null);
        MemoDTO memo2 = new MemoDTO(2, "Memo content2", null);
        MemoDTO memo3 = new MemoDTO(3, "Memo content3", null);
        List<MemoDTO> memos = Arrays.asList(memo1, memo2, memo3);
        when(mockMemoService.getAllMemos()).thenReturn(Flux.fromIterable(memos));
        // when
        WebTestClient.ResponseSpec exchange = webTestClient.get().uri("/rest/memos").accept(MediaType.APPLICATION_XML).exchange();
        // then
        exchange.expectStatus().isOk();
        verify(mockMemoService).getAllMemos();
    }

    @Test
    void shouldReturn200AndOneRecommendedMemoWhenSuggestedMemoIsCalledWithJson() throws Exception {
        // given
        MemoDTO memoDTO = new MemoDTO(1, "Suggested Memo Content", null);
        when(mockMemoService.retrieveSuggestedMemo()).thenReturn(Mono.just(memoDTO));
        // when
        WebTestClient.ResponseSpec exchange = webTestClient.get().uri("/rest/SuggestedMemo").accept(MediaType.APPLICATION_JSON).exchange();
        // then
        exchange.expectBody(MemoDTO.class).isEqualTo(memoDTO);
        verify(mockMemoService, times(1)).retrieveSuggestedMemo();
    }
//
    @Test
    void shouldReturn200AndRequestedMemoWhenSelectedMemoIsCalled() throws Exception {
        // given
        MemoDTO memoDTO = new MemoDTO(1, "Memo Content", null);
        when(mockMemoService.findOne(1)).thenReturn(Mono.just(memoDTO));
        // when
        WebTestClient.ResponseSpec exchange = webTestClient.get().uri("/rest/memo/{id}", 1).accept(MediaType.APPLICATION_JSON).exchange();
        // then
        exchange.expectStatus().isOk().expectBody(MemoDTO.class).isEqualTo(memoDTO);
        verify(mockMemoService).findOne(1);
    }
//
    @Test
    void shouldReturnErrorJsonWhenRedisConnectionFailureExceptionIsThrown() throws Exception {
        // given
        when(mockMemoService.findOne(1)).thenThrow(new RedisConnectionFailureException("Connection timeout test!"));
        // when
        WebTestClient.ResponseSpec exchange = webTestClient.get().uri("/rest/memo/{id}", "1").exchange();

        // then
        exchange.expectStatus().is5xxServerError();
//                .expectBody().jsonPath("$.message").isNotEmpty()
//                .jsonPath("%.message").isEqualTo("SERVER_ERROR");
        verify(mockMemoService).findOne(1);
    }

}
