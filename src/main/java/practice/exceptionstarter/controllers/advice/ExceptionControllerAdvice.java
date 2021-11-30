package practice.exceptionstarter.controllers.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import practice.exceptionstarter.exceptions.ErrorConst;
import practice.exceptionstarter.exceptions.ResourceDuplicatedException;
import practice.exceptionstarter.exceptions.ResourceNotFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/11/28
 */

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ExceptionControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResBody handleResourceNotFoundException(HttpServletRequest request, final ResourceNotFoundException e) {
        log.error("requestURI: {}, errorMessage: {}", request.getRequestURI(), e.getMessage());
        return new ErrorResBody(HttpStatus.NOT_FOUND, ErrorConst.RESOURCE_NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResBody handleResourceDuplicatedException(HttpServletRequest request, final ResourceDuplicatedException e) {
        log.error("requestURI: {}, errorMessage: {}", request.getRequestURI(), e.getMessage());
        return new ErrorResBody(HttpStatus.BAD_REQUEST, ErrorConst.RESOURCE_DUPLICATED, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResBody handleTypeMismatchException(HttpServletRequest request, final MethodArgumentTypeMismatchException e) {
        log.error("requestURI: {}, errorMessage: {}", request.getRequestURI(), e.getMessage());
        return new ErrorResBody(HttpStatus.BAD_REQUEST, ErrorConst.TYPE_MISMATCH, getTypeMismatchErrorMessage(e));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResBody handleInvalidRequestBodyException(HttpServletRequest request, final HttpMessageNotReadableException e) {
        log.error("requestURI: {}, errorMessage: {}", request.getRequestURI(), e.getMessage());
        return new ErrorResBody(HttpStatus.BAD_REQUEST, ErrorConst.INVALID_REQUEST_BODY, e.getMessage());
    }

    private String getTypeMismatchErrorMessage(MethodArgumentTypeMismatchException e) {
        StringBuilder errorMessageBuilder = new StringBuilder();
        if (StringUtils.hasText(e.getName())) {
            errorMessageBuilder.append(e.getName());
        } else {
            errorMessageBuilder.append("Argument");
        }
        errorMessageBuilder.append(String.format(" [%s] 의 타입이 올바르지 않습니다. 올바른 타입은 %s 입니다.",
                e.getValue(),
                e.getRequiredType()));

        return errorMessageBuilder.toString();
    }
}
