package practice.exceptionstarter.exceptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/11/17
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessageConst {
    public static final String RESOURCE_NOT_FOUND = "요청한 리소스가 존재하지 않습니다.";
    public static final String RESOURCE_DUPLICATED = "요청한 리소스가 1개 이상 존재합니다.";
}
