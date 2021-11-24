# exception-starter
> spring-boot Web MVC exception 처리 관련

## servlet 예외
### 순수 servlet 예외
> 컨트롤러 메서드 내에서 Exception 이 발생하거나 response.sendError 메서드를 호출하면  
> 1.컨트롤러 -> 인터셉터 -> 서블릿 -> 필터 -> WAS 로 해당 error 전파   
> 2.WAS 에 올라온 Exception 혹은 Error 를 WebServerCustomizer 에서 설정한 URI 로 재요청 -> 필터 -> 서블릿 -> 인터셉터 -> 컨트롤러  
> 3.예외 컨트롤러(여기서는 ErrorPageController)에 정의한 Page 를 client 에 보여준다.  

## API 예외
