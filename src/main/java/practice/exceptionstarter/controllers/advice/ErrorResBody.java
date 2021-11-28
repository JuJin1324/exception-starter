package practice.exceptionstarter.controllers.advice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/11/17
 */

@Getter
public class ErrorResBody {
    private final Integer status;
    private final String  code;
    private final String  message;
    private final String  error;

    @JsonProperty("timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime timestampKST;

    public ErrorResBody(HttpStatus httpStatus, String error, String message) {
        this.status = httpStatus.value();
        this.code = httpStatus.getReasonPhrase();
        this.message = message;
        this.error = error;
        this.timestampKST = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
