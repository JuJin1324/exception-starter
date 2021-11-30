package practice.exceptionstarter.exceptions;

import lombok.Getter;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/08/22
 */
@Getter
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName) {
        super(ErrorMessageConst.RESOURCE_NOT_FOUND + String.format(" [%s]", resourceName));
    }
}
