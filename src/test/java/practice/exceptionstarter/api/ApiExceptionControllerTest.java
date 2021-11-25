package practice.exceptionstarter.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/11/25
 */

@SpringBootTest
class ApiExceptionControllerTest {
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(@Autowired ApiExceptionController apiExceptionController) {
        mockMvc = MockMvcBuilders.standaloneSetup(apiExceptionController)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 한글 깨짐 처리
                .alwaysDo(print())
                .build();
    }

    @Test
    void getMember() throws Exception {
        mockMvc.perform(get("/api/members/{id}", "spring")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberId").value("spring"))
                .andExpect(jsonPath("$.name").value("hello spring"));
    }
}
