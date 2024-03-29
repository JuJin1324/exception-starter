package practice.exceptionstarter.controllers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;
import practice.exceptionstarter.controllers.advice.ExceptionControllerAdvice;
import practice.exceptionstarter.exceptions.ErrorConst;
import practice.exceptionstarter.exceptions.ErrorMessageConst;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/11/25
 */

@SpringBootTest
class ApiExceptionControllerTest {
    public static final Long   MEMBER_ID_NOT_EXIST     = 1L;
    public static final Long   MEMBER_ID_DUPLICATED    = 2L;
    public static final String MEMBER_ID_TYPE_MISMATCH = "qqqq";

    private MockMvc      mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    ApiExceptionController    apiExceptionController;
    @Autowired
    ExceptionControllerAdvice exceptionControllerAdvice;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(apiExceptionController)
                .setControllerAdvice(exceptionControllerAdvice)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 한글 깨짐 처리
                .alwaysDo(print())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("[비지니스 로직 에러] 존재하지 않는 사용자")
    void getMember_whenNotExistUser_thenNotFound() throws Exception {
        req_notFound(
                get("/api/members/{id}", MEMBER_ID_NOT_EXIST),
                ErrorConst.RESOURCE_NOT_FOUND,
                ErrorMessageConst.RESOURCE_NOT_FOUND + " [member]"
        );
    }

    @Test
    @DisplayName("[비지니스 로직 에러] 중복된 사용자 조회")
    void getMember_whenNotDuplicatedUser_thenBadRequest() throws Exception {
        req_badRequest(
                get("/api/members/{id}", MEMBER_ID_DUPLICATED),
                ErrorConst.RESOURCE_DUPLICATED,
                ErrorMessageConst.RESOURCE_DUPLICATED + " [member]"
        );
    }

    @Test
    @DisplayName("[타입 에러] PathVariable 타입 에러")
    void getMember_whenTypeMismatchPathVariable() throws Exception {
        req_badRequest(
                get("/api/members/{memberId}", MEMBER_ID_TYPE_MISMATCH),
                ErrorConst.TYPE_MISMATCH,
                String.format("memberId [%s] 의 타입이 올바르지 않습니다. 올바른 타입은 class java.lang.Long 입니다.",
                        MEMBER_ID_TYPE_MISMATCH)
        );
    }

    @Test
    @DisplayName("[타입 에러] RequestParam 타입 에러")
    void getMember_whenTypeMismatchRequestParam() throws Exception {
        req_badRequest(
                get("/api/members/requestParam?memberId={memberId}", MEMBER_ID_TYPE_MISMATCH),
                ErrorConst.TYPE_MISMATCH,
                String.format("memberId [%s] 의 타입이 올바르지 않습니다. 올바른 타입은 class java.lang.Long 입니다.",
                        MEMBER_ID_TYPE_MISMATCH)
        );
    }

    @Test
    @DisplayName("[타입 에러] RequestBody 타입 에러")
    void getMember_whenInvalidRequestBody() throws Exception {
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("name", "test");
        reqBody.put("age", "invalid");  /* age 는 Integer 인데 String 을 넣었다. */

        req_badRequest(
                post("/api/members")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reqBody)),
                ErrorConst.INVALID_REQUEST_BODY,
                null
        );
    }

    private void req_badRequest(MockHttpServletRequestBuilder builder, String error, String message) throws Exception {
        ResultActions resultActions = mockMvc.perform(builder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("Bad Request"))
                .andExpect(jsonPath("$.error").value(error));
        if (message != null) {
            resultActions
                    .andExpect(jsonPath("$.message").value(message));
        }
    }

    private void req_notFound(MockHttpServletRequestBuilder builder, String error, String message) throws Exception {
        mockMvc.perform(builder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("Not Found"))
                .andExpect(jsonPath("$.error").value(error))
                .andExpect(jsonPath("$.message").value(message));
    }
}
