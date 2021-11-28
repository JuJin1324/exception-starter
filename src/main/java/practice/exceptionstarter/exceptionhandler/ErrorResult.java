package practice.exceptionstarter.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/11/28
 */

@Data
@AllArgsConstructor
public class ErrorResult {
    private String code;
    private String message;
}
