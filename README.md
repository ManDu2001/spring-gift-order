# spring-gift-order

### 1단계 진행전 리팩토링 사항
-[x] [Exception] 현재 존재하는 커스텀 예외들의 공통 부모 클래스 CustomException Class 만들고 상속하여, GlobalExceptionHandler에 적용해 중복 코드 최소화

### 1단계 요구사항 진행 목록
-[x] REST-API-KEY 를 별도로 보관하는 application-local.properties 파일 생성(단, 이 파일은 git에 업로드 하지 않고, local에서만 가지고 있을 것입니다.)
-[x] application.properties에 "spring.profiles.active=local" 추가 
-[x] [Config] application-local.properties에 있는 설정값 필드를 자바 객체로 매핑시켜주는 KakaoProperties 클래스 생성
-[x] [Service] 인가 코드를 이용해 access token을 얻어 오는 역할 수행하는 KakaoOAuthService 인터페이스 와 구현체 KakaoOAuthServiceImpl 작성
-[x] [Controller] 카카오 로그인 후 카카오 서버가 localhost:8080으로 리디렉션 할 때 포함된 code를 이용해 service layer 호출하는 역할 수행하는 KakaoOAuthController 구현
