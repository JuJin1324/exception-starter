# exception-starter
> spring-boot Web MVC exception 처리 관련

## servlet 예외
### 테스트 방법
> App 실행 후 브라우저에서   
> `http://localhost:8080/error-ex`  
> `http://localhost:8080/error-404`  
> `http://localhost:8080/error-400`  
> `http://localhost:8080/error-500`  

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

### 스프링 부트 vs 순수 servlet 예외 Page 처리
> 순수 servlet 예외 Page 처리의 경우 컨트롤러에서 예외 발생 시 WAS 까지 예외가 올라가지만 스프링 부트의 BasicErrorController 예외 처리의 경우 
> WAS 까지 예외전파가 되지 않고 스프링 MVC 내에서 처리한다.  
> 중요) WAS 까지 예외 전파를 하게 되면 JUnit 테스트가 불가능해진다.(JUnit 테스트에서는 WAS 를 붙이지 않기 때문에)   

## API 예외
### 테스트 방법
> `test/java/practice.exceptionstarter.controllers.api.ApiExceptionControllerTest` 에서 JUnit 테스트 실행

### ControllerAdvice
ControllerAdvice Annotation
> @RestController 애노테이션이 붙은 컨트롤러들에서만 이 advice 에서 Exception 처리를 한다. - 
> `@RestControllerAdvice(annotations = RestController.class)`  
> 지정된 패키지에 있는 컨트롤러들에서만 이 advice 에서 Exception 처리를 한다. - 
> `@RestControllerAdvice("practice.exceptionstarter.controllers.api")`  
> 지정된 컨트롤러 및 컨트롤러의 자식 클래스에서 이 advice 에서 Exception 처리를 한다. - 
> `@RestControllerAdvice(assignableTypes = {ApiExceptionController.class, ApiExceptionV2Controller.class})`    

