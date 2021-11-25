# exception-starter
> spring-boot Web MVC exception 처리 관련

## servlet 예외
### 순수 servlet 예외 Page 처리
> 컨트롤러 메서드 내에서 Exception 이 발생하거나 response.sendError 메서드를 호출하면  
> 1.컨트롤러 -> 인터셉터 -> 서블릿 -> 필터 -> WAS 로 해당 error 전파   
> 2.WAS 에 올라온 Exception 혹은 Error 를 WebServerCustomizer 에서 설정한 URI 로 재요청 -> 필터 -> 서블릿 -> 인터셉터 -> 컨트롤러  
> 3.예외 컨트롤러(여기서는 ErrorPageController)에 정의한 Page 를 client 에 보여준다.  

### 스프링 부트 예외 Page 처리
> 스프링 부트에서는 스프링 부트에서 자동 등록한 BasicErrorController 를 통해서 에러 Page 처리를 한다.    
> 컨트롤러(예외 발생) -> BasicErrorController -> Error View 로 처리된다.    
> BasicErrorController 의 View Page 처리 순서는 다음과 같다.  
>
>1.뷰 탬플릿(동적 페이지로 구현할 경우)  
> resources/templates/error/500.html    
> resources/templates/error/5xx.html    
>
> 2.정적 리소스(정적 페이지로 구현할 경우)  
> resources/static/error/400.html  
> resources/static/error/4xx.html  
> 
> 3.적용 대상이 없을 때(위 1,2가 없을 때 등등)     
> resources/templates/error.html  

## API 예외
