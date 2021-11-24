package practice.exceptionstarter.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/11/17
 */

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();

        String uuid = UUID.randomUUID().toString();

        try {
            if (request.getDispatcherType().equals(DispatcherType.REQUEST)) {
                log.info("1.정상 요청이 필터를 타고 컨트롤러로 이동");
            } else if (request.getDispatcherType().equals(DispatcherType.ERROR)) {
                log.info("4.WAS 까지 올라간 예외를 WAS 에서 WebServerCustomizer 에 설정된 error 페이지로 재요청");
            }
            log.info("REQUEST [{}][{}][{}]", uuid, request.getDispatcherType(), requestURI);
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.info("3.컨트롤러에서 던진 예외 필터에서 catch: {}", e.getMessage());
            throw e;
        } finally {
            log.info("RESPONSE [{}][{}][{}]", uuid, request.getDispatcherType(), requestURI);
        }
    }
}
