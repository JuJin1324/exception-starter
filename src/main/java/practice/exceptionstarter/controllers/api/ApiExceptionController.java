package practice.exceptionstarter.controllers.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;
import practice.exceptionstarter.exceptions.ResourceDuplicatedException;
import practice.exceptionstarter.exceptions.ResourceNotFoundException;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/11/25
 */

@RestController
public class ApiExceptionController {

    @GetMapping("/api/members/{memberId}")
    public MemberDto getMember(@PathVariable("memberId") Long memberId) {
        if (memberId.equals(1L)) {
            throw new ResourceNotFoundException("member");
        }
        if (memberId.equals(2L)) {
            throw new ResourceDuplicatedException("member");
        }
        return null;
    }

    @GetMapping("/api/members/requestParam")
    public MemberDto typeMismatchForRequestParam(@RequestParam("memberId") Long memberId) {
        return new MemberDto(1L, "test");
    }

    @PostMapping("/api/members")
    public MemberDto typeMismatchForRequestBody(@RequestBody CreateMemberReqBody reqBody) {
        return new MemberDto(1L, "test");
    }

    @Data
    @NoArgsConstructor
    static class CreateMemberReqBody {
        private String name;
        private Integer age;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class MemberDto {
        private Long memberId;
        private String name;
    }
}
