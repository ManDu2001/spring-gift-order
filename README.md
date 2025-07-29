# spring-gift-order

### 1단계 진행전 리팩토링 사항
-[x] [Exception] 현재 존재하는 커스텀 예외들의 공통 부모 클래스 CustomException Class 만들고 상속하여, GlobalExceptionHandler에 적용해 중복 코드 최소화

### 1단계 요구사항 진행 목록
-[x] REST-API-KEY 를 별도로 보관하는 application-local.properties 파일 생성(단, 이 파일은 git에 업로드 하지 않고, local에서만 가지고 있을 것입니다.)
-[x] application.properties에 "spring.profiles.active=local" 추가 
-[x] [Config] application-local.properties에 있는 설정값 필드를 자바 객체로 매핑시켜주는 KakaoProperties 클래스 생성
-[x] [Service] 인가 코드를 이용해 access token을 얻어 오는 역할 수행하는 KakaoOAuthService 인터페이스 와 구현체 KakaoOAuthServiceImpl 작성
-[x] [Controller] 카카오 로그인 후 카카오 서버가 localhost:8080으로 리디렉션 할 때 포함된 code를 이용해 service layer 호출하는 역할 수행하는 KakaoOAuthController 구현

### 1단계 피드백 반영 목록
- [x] [Exception] CustomException을 상속받는 예외들의 getType 함수에서 "localhost:8080" 으로 하드 코딩된 URI를 제거 (아직, 해당 URI에 문서가 존재하지 않기 때문에 큰 문제는 없다고 생각됩니다.)
- [x] [properties] 기존 properties 확장자로 관리되는 설정 파일을 yaml 확장자로 변경
- [x] [Dto] KakaoOAuthResponseDto 추가
- [x] [Controller] KakaoOAuthController의 receiveCode의 반환 타입 String -> KakaoOAuthResponseDto 로 수정
- [x] [Service] KakaoOAuthServiceImpl의 getAccessToken 함수에서 기존 JSON Node 의 각 필드 접근하는 방식 대신, 바로 KakaoOAuthResponseDto 매핑되도록 변경하여 코드 간소화

### 2단계 기능 구현 사항 목록
- [x] [README.md] 2단계 요구사항에 따른 작업 목록 작성
- [x] [resources/schema.sql] orders 에 대한 table 정의문 추가 
- [x] [Domain] 주문(Order)에 대한 정보를 담는 Order entity 생성
- [x] [Domain] 각 유저에 대한 access, refresh 토큰과 만료일을 필드로 갖는 UserKakaoToken entity 생성
- [x] [Domain] 기존 MemberEntity에 Order와의 연관 관계 설정
- [x] [Domain] Option entity에서 옵션 개수를 차감시키는 메소드 추가
- [x] [DTO] 주문을 요청하고, 그 결과를 반환하는 역할 수행하는 OrderRequestDto, OrderResponseDto 생성하고 request의 경우 validation 적용
- [x] [DTO] 카카오 로그인시 멤버와 access, refresh token, 만료일 반환하는 LoginResponseDto 생성
- [x] [DTO] 액세스 토큰을 이용해 카카오 유저 정보를 가져오고, 결과적으로 해당 유저의 Email을 가지고있는 KakaoUserInfoResponseDto 생성
- [x] [Repository] Order와 UserKakaoToken에 대한 JPA기반 Repository 생성
- [x] [Service] 사용자에게 주문 완료시 메세지를 보내기 위한 KakaoMessageService 인터페이스와 그 구현체 작성
- [x] [Service] 주문을 요청(생성)하기 위한 OrderService 인터페이스와 그 구현체 작성
- [x] [Service] "카카오 로그인을 기반으로" 기존 MemberRepository에 회원가입, 로그인 수행하는 메소드 추가 / 또한 transactional readonly 잘못된 부분 수정
- [x] [Service] 기존 KakaoOAuthService에서 액세스 토큰으로 사용자 정보 받아 오는 기능 추가 및, 인가 코드로 접근시 회원가입, 로그인 진행시키는 기능 추가
- [x] [Exception] 옵션 Id에 대해서 존재하지 않는 경우에 대한 커스텀 예외 생성 (OptionIdNotFoundException)
- [x] [Controller] 주문 요청 수행하는 OrderController 생성
- [x] [Controller] KakaoOAuthController에서 기존 receiveCode 는 단지 인가 코드로 access token을 디버깅 용도로 보여주는 역할을 수행했지만, 인가 코드를 통해 회원가입, 로그인 진행하고, 결과 반환하도록 수정
- [x] [Security] @LoginKakaoMember 어노테이션 생성 및 이를 처리하는 Resolver 생성 
- [x] [Security] LoginKakaoMemberArgumentResolver를 기존 WebConfig에 등록
- [x] [Security] JwtAuthenticationFilter에서 "/api/orders" 로 시작하는 경로에 대해서 검사하지 않도록 수정(order는 jwt 토큰이 아니라 access token으로 검증 진행하기 때문에)
