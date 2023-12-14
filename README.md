# exception-starter

## 예외 처리 전략
### 복구 가능한 오류와 불가능한 오류 구분하기
> 가장 먼저 할 것은 복구 가능한 오류와 복구 불가능한 오류를 구분하는 것이다. 모든 예외에 대해서 동일한 방식을 적용할 수는 없다.
> 어떤 예외는 상시로 발생해서 무시할 수 있으며, 어떤 예외는 무시하면 절대 안되는 경우도 있다.  
> 이들을 구분없이 다룬다면 사용자는 불편하고, 개발자는 상시로 발생하는 알람으로 점점 더 시스템의 문제에 등한시 하게 된다.  
> 그래서 이들을 구분해서 예외가 발생했을때 어떻게 처리할지 결정해야 한다.

### 복구 가능한 오류
> 복구 가능한 오류는 일반적으로 시스템 외적인 요소로 발생하는 치명적이지 않은 오류이다.
> 사용자가 잘못된 전화번호를 입력한다면 이는 시스템을 멈춰야할 정도의 문제가 아니다.
> 사용자에게 전화번호가 잘못되었으니 다시 입력하라는 오류 메세지를 제공하고 다시 입력받으면 된다.
> 마찬가지로 네트워크 오류가 발생했다면 잠시 후, 다시 요청하면 된다.
> 
> 이러한 오류는 프로그램이 감지하고 적절한 조치를 취한 후 정상적으로 계속 실행될 수 있는 오류이다.
> * 사용자의 오입력
> * 네트워크 오류
> * 서비스적으로 중요하지 않은 작업의 오류
> 
> 복구 가능한 오류는 상시로 발생할 수 있다고 가정하고, 사용자 (호출자) 에게 가능한 문제 원인을 인지할 수 있게 해야한다.

### 복구 불가능한 오류
> 복구 불가능한 오류는 별도의 조치 없이 시스템이 자동으로 복구할 수 있는 방법이 없는 경우이다.
> 대부분의 경우, 이 오류의 원인을 해결하기 전에 프로그램이 계속 실행될 수 없다.
> 
> * 메모리 부족 (Out of Memory)
> 시스템이 필요한 메모리를 할당받지 못할 때 발생한다.
> * 스택오버플로우 (StackOverflow)
> 재귀 함수가 너무 깊게 호출되어 스택 메모리가 고갈될 때 발생한다.
> * 시스템 레벨의 오류
> 하드웨어 문제나 운영 체제의 중대한 버그로 인해 발생한다.
> * 개발자의 잘못 구현된 코드
> 복구 불가능한 오류는 자주 발생하는 오류가 아니기 때문에 빠르게 개발자에게 문제 원인을 알려야한다.
> 
> 이를 위해 로그 레벨을 error로 두고, 로그에서는 에러 트레이스를 남긴 뒤, 임계치를 초과하면 개발자에게 알람을 보내도록 구성해야한다.  

### 추적 가능한 예외
> 실패한 코드의 의도를 파악하려면 호출 스택만으로 부족하다. 그래서 다음의 내용이 예외에 담겨야 한다.
> * 오류 메세지에 어떠한 값을 사용하다가 실패하였는지
> * 실패한 작업의 이름과 실패 유형
> 
> 이들이 포함되어 있어야 운영 환경에서 예외가 발생했을 때 조금이라도 정확하고 빠르게 대응 가능해진다.  
> ```java
> // bad 
> throw new IllegalArgumentException('잘못된 입력입니다.');
> 
> // good
> throw new IllegalArgumentException(`사용자 ${userId}의 입력(${inputData})가 잘못되었다.`);
> ```

### 의미를 담고 있는 예외
> 예외의 이름은 그 예외의 원인과 내용을 정확하게 반영해야 한다.
> 코드를 읽는 사람이 예외 이름만 보고도 해당 예외가 왜 발생했는지 어느 정도 추측할 수 있어야 한다.
> 
> 이는 크게 2가지 이유가 있다.
> 
> * 코드의 가독성 향상: 의미 있는 이름을 가진 예외는 코드를 읽는 사람에게 문맥을 제공한다.
> * 디버깅 용이성: 오류의 원인을 빠르게 파악하고 수정할 수 있다.
> ```java
> // bad
> class CustomException extends RuntimeException {}
> void connectToDatabase() {
>     throw new CustomException("Connection failed because of invalid credentials.");
> }
>  
> // good
> // 위 예외는 너무 포괄적인 의미를 담고 있다. (CustomException)
> // 이를 좀 더 유의미한 예외로 만들어서 개선할 수 있다.
> class InvalidCredentialsException extends RuntimeException {}
> 
> void connectToDatabase() {
>     throw new InvalidCredentialsException("Failed to connect due to invalid credentials.");
> }
> ```
> 개선된 코드에서는 InvalidCredentialsException 라는 예외 이름을 사용하여 데이터베이스 연결 시 발생하는 인증 오류를 명확하게 나타낸다.

### 예외 계층 구조 만들기
> 예외를 가능한 계층 구조로 만들어서 사용한다.
> "의미를 담고 있는 예외", "Layer에 맞는 예외" 등 내용을 따르다보면 수많은 Custom Exception들이 생성된다.
> 이를 용도에 맞게 분류할 필요가 있으며, 이렇게 기준에 맞게 분류한 Exception들은 그에 맞게 일관된 처리 방법을 적용할 수 있다.
> 
> ```java
> // bad
> class DuplicatedException extends RuntimeException {}
> class UserAlreadyRegisteredException extends RuntimeException {}
> 
> // good
> // 아래와 같이 목적에 맞는 Custom Exception 을 분류할 수 있는 상위 Exception 을 가지도록 한다.
> class ValidationException extends RuntimeException {}
> class DuplicatedException extends ValidationException {}
> class UserAlreadyRegisteredException extends ValidationException {}
> ```

### 외부의 예외 감싸기
> 외부 SDK, 외부 API를 통해 발생하는 예외들은 하나로 묶어서 처리한다.
> 이는 바로 직전의 예외 계층 구조 만들기에 연관된다.
> 
> 예를 들어 다음과 같이 외부 결제 서비스의 SDK를 사용하는 경우가 있을 경우 이들을 묶어서 처리할 수 있다.
> 
> // bad
> function order() {
>     const pay = new Pay();
>     try{
>         pay.billing();
>         database.save(pay);
>     } catch (e) {
>         logger.error(`pay fail`, e);
>     }
> }
> 이렇게 한번에 catch를 할 경우 구체적으로 어디서 어떤 문제가 발생했으며, 그에 따른 다양한 해결방법을 포함시키기가 어렵다.
> 
> 외부 라이브러리 (pay.billing) 에서 발생하는 문제와 우리가 관리하는 코드는 (databse.save) 가 같은 방식으로 해결해서는 안된다.
> 특히 결제 서비스의 경우 사용자의 문제로 인해 오류가 발생할 수 있는 상시적인 문제를 포함하고 있다.
> 
> 이를 위해 다음과 같이 외부 라이브러리가 발생시키는 모든 예외를 처리하는 것 또한 문제이다.
> 
> // bad
> function order() {
>     const pay = new Pay();
>     try{
>         pay.billing();
>         database.save(pay);
>     } catch (e) {
>         if(e instanceof PayNetworkException) {
>             ...
>         } else if (e instanceof EmptyMoneyException) {
>             ...
>         } else if (e instanceof PayServerException) {
>             ...
>         }
>         ...
>     }
> }
> 이렇게 되면, 결제 서비스가 교체되어 발생하는 에러의 종류가 달라지면 서비스 코드 전체에 영향을 끼치게 된다.
> 
> 그래서 다음과 같이 둘 간의 의존성을 분리해야만한다.
> 
> // good
> 
> function billing() {
>     try {
>         pay.billing();
>     } catch (e) {
>     if(e instanceof PayNetworkException) {
>         ...
>     } else if (e instanceof EmptyMoneyException) {
>         ...
>     } else if (e instanceof PayServerException) {
>         ...
>     }
>     ...
>     throw new BillingException (e);
> }
>     
> function order() {
>     const pay = new Pay();
>     
>     try{
>         pay.billing();
>         database.save(pay);
>     } catch (e) {
>         pay.cancel();  
>     }
> }
> BillingException 은 우리 서비스의 예외이기 때문에 가능한 가장 먼 곳에서 처리한다 (미들웨어, 글로벌 에러 핸들러 등)
> DB 저장이 실패하면 결제된 요청도 취소 처리한다.
> 이렇게 처리하면 외부 라이브러리 (결제 서비스) 와 우리 서비스 (order) 간 의존성이 분리된다.

### 참조사이트
> [좋은 예외(Exception) 처리](https://jojoldu.tistory.com/734)

---

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

