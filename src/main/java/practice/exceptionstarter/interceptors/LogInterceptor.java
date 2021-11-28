package practice.exceptionstarter.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/11/19
 */
@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        request.setAttribute(LOG_ID, uuid);

        if (request.getDispatcherType().equals(DispatcherType.REQUEST)) {
            log.info("1.정상 요청이 인터셉터를 타고 컨트롤러로 이동");
        } else if (request.getDispatcherType().equals(DispatcherType.ERROR)) {
            log.info("4.WAS 까지 올라간 예외를 WAS 에서 WebServerCustomizer 에 설정된 error 페이지로 재요청");
        }

        /* @RequestMapping 요청시 -> handler 의 타입이 HandlerMethod
         * static Resource 요청시 -> ResourceHttpRequestHandler */
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
        }
        log.info("REQUEST [{}][{}][{}][{}]", uuid, request.getDispatcherType(), requestURI, handler);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandler: [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String logId = (String) request.getAttribute(LOG_ID);
        log.info("RESPONSE [{}][{}][{}][{}]", logId, request.getDispatcherType(), requestURI, handler);

        if (ex != null) {
            log.info("3.컨트롤러에서 던진 예외 인터셉터의 afterCompletion 에서 catch: {}", ex.getMessage());
        }
    }
}
