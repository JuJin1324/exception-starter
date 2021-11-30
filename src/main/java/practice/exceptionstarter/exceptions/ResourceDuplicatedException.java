package practice.exceptionstarter.exceptions;

import lombok.Getter;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/11/08
 */
@Getter
public class ResourceDuplicatedException extends RuntimeException {
    public ResourceDuplicatedException(String resourceName) {
        super(ErrorMessageConst.RESOURCE_DUPLICATED + String.format(" [%s]", resourceName));
    }
}
