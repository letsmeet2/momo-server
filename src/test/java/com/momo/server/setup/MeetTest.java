package com.momo.server.setup;


import java.time.LocalDate;
import java.util.ArrayList;

import com.momo.server.setup.apicontroller.MeetTestController;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.momo.server.dto.request.MeetSaveRequestDto;
import com.momo.server.repository.MeetRepository;
import com.momo.server.repository.TimeSlotRepository;
import org.springframework.test.web.servlet.MvcResult;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS) // @AfterAll 어노테이션에는 필요함
public class MeetTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MeetRepository meetRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    private MeetTestController meetTestController;

    @BeforeEach
    void setUp() throws Exception {
        meetTestController = new MeetTestController(mockMvc, objectMapper);
    }

    @AfterAll
    public void afterAll() {// 생성된 약속 찾아서 지워주기 @Transactional이 동작안해서직접 구현함
        meetRepository.findAllAndRemoveMeet("e8e1bf2ec5ab131");
        timeSlotRepository.findAllAndRemoveTimeSlot("e8e1bf2ec5ab131");
    }

    @Test
    public void 약속을_생성() throws Exception {

        // given
        ArrayList<LocalDate> testDate = new ArrayList<LocalDate>();

        testDate.add(LocalDate.parse("2021-07-20"));
        testDate.add(LocalDate.parse("2021-07-30"));

        MeetSaveRequestDto meetSaveRequestDto = MeetSaveRequestDto.builder().title("약속생성테스트").start("11:00")
                .end("19:00").dates(testDate).gap(30).video(true).center(true).build();

        MvcResult mvcResult = meetTestController.createMeet(meetSaveRequestDto);
        // 참조 https://engkimbs.tistory.com/858
        // http://honeymon.io/tech/2019/10/23/spring-deprecated-media-type.html
    }

    @Test
    public void 약속조회() throws Exception {

        String meetId = "ea3d15b1adf87f5";
        meetTestController.getMeet(meetId);
    }

}