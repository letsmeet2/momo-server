package com.momo.server.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.momo.server.dto.request.MeetSaveRequestDto;


@AutoConfigureMockMvc
@SpringBootTest
public class MeetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;// json매핑을 위해

    @Test
    public void 약속을_생성() throws Exception {


        // given
        ArrayList<LocalDate> testDate = new ArrayList<LocalDate>();

        testDate.add(LocalDate.parse("2021-07-20"));
        testDate.add(LocalDate.parse("2021-07-30"));

        MeetSaveRequestDto meetSaveRequestDto =
                MeetSaveRequestDto.builder().title("약속생성테스트").start("11:00").end("19:00")
                        .dates(testDate).gap(30).video(true).center(true).build();

        // when & then

        mockMvc.perform(post("/api/meet").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(meetSaveRequestDto))).andDo(print())
                .andExpect(status().isCreated());

        // 참조 https://engkimbs.tistory.com/858
        // http://honeymon.io/tech/2019/10/23/spring-deprecated-media-type.html
    }

    //
    // @Test
    // public void 약속조회() throws Exception {
    //
    // String meetId = "3a71e9276e1621e";
    //
    // mockMvc.perform(get("/api/meet/" + meetId)) // 1, 2
    // .andExpect(status().isOk()) // 4
    // .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    // .andExpect(jsonPath("$.title").value("14테스트sdf")).andDo(print());
    //
    // }

}