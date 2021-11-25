package practice.exceptionstarter.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/11/20
 */

@Slf4j
@Controller
public class ServletExController {

    @GetMapping("/error-ex")
    public void errorEx() {
        log.info("2.컨트롤러에서 예외 발생");
        throw new RuntimeException("예외 발생!");
    }

    @GetMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException {
        log.info("2.컨트롤러에서 response.sendError 호출");
        response.sendError(404, "404 오류!");
    }

    @GetMapping("/error-400")
    public void error400(HttpServletResponse response) throws IOException {
        log.info("2.컨트롤러에서 response.sendError 호출");
        response.sendError(400, "400 오류!");
    }

    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException {
        log.info("2.컨트롤러에서 response.sendError 호출");
        response.sendError(500);
    }
}
