package gift.service;


import gift.config.KakaoProperties;
import gift.domain.Member;
import gift.domain.UserKakaoToken;
import gift.dto.KakaoOAuthResponseDto;
import gift.dto.KakaoUserInfoResponseDto;
import gift.dto.LoginResponseDto;
import gift.repository.UserKakaoTokenRepository;
import gift.security.JwtProvider;
import java.time.Instant;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Service
public class KakaoOAuthServiceImpl implements KakaoOAuthService {

  private final KakaoProperties kakaoProperties;
  private final RestClient restClient;

  private final MemberService memberService;

  private final JwtProvider jwtProvider;

  private final UserKakaoTokenRepository userKakaoTokenRepository;

  public KakaoOAuthServiceImpl(KakaoProperties kakaoProperties, MemberService memberService, JwtProvider jwtProvider, UserKakaoTokenRepository userKakaoTokenRepository) {
    this.kakaoProperties = kakaoProperties;
    this.restClient = RestClient.builder()
        .baseUrl("https://kauth.kakao.com")
        .build();
    this.memberService = memberService;
    this.jwtProvider = jwtProvider;
    this.userKakaoTokenRepository = userKakaoTokenRepository;
  }

  @Override
  public KakaoOAuthResponseDto getAccessToken(String authorizationCode) {
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "authorization_code");
    body.add("client_id", kakaoProperties.getClientId());
    body.add("redirect_uri", kakaoProperties.getRedirectUri());
    body.add("code", authorizationCode);

    try {
      KakaoOAuthResponseDto response = restClient.post()
          .uri("/oauth/token")
          .contentType(MediaType.APPLICATION_FORM_URLENCODED)
          .body(body)
          .retrieve()
          .body(KakaoOAuthResponseDto.class);

      if (response == null || response.accessToken() == null) {
        throw new IllegalStateException("카카오에서 access_token을 받지 못했습니다.");
      }

      return response;

    } catch (RestClientResponseException e) {
      throw new IllegalStateException("카카오 토큰 요청 실패: " + e.getResponseBodyAsString());
    }
  }
  @Override
  public KakaoUserInfoResponseDto getUserInfo(String accessToken) {
    try {
      return restClient.get()
          .uri("https://kapi.kakao.com/v2/user/me")
          .header("Authorization", "Bearer " + accessToken)
          .retrieve()
          .body(KakaoUserInfoResponseDto.class);
    } catch (RestClientResponseException e) {
      throw new IllegalStateException("카카오 사용자 정보 요청 실패: " + e.getResponseBodyAsString());
    }
  }

  public LoginResponseDto registerOrLogin(String authorizationCode) {
    KakaoOAuthResponseDto tokenDto = getAccessToken(authorizationCode);
    KakaoUserInfoResponseDto userInfo = getUserInfo(tokenDto.accessToken());

    String email = userInfo.email();

    if (email == null || email.isEmpty()) {
      throw new IllegalStateException("카카오에서 이메일을 받아올 수 없습니다. 회원가입 불가.");
    }

    Member member = memberService.registerOrLoginByKakao(email, tokenDto);

    String jwtToken = jwtProvider.generateToken(member);

    return new LoginResponseDto(
        tokenDto.tokenType(),
        tokenDto.accessToken(),
        tokenDto.expiresIn(),
        tokenDto.refreshToken(),
        tokenDto.refreshTokenExpiresIn(),
        jwtToken
    );
  }

  @Override
  @Transactional(readOnly = true)
  public Member findMemberByAccessToken(String accessToken) {
    UserKakaoToken token = userKakaoTokenRepository.findByAccessToken(accessToken)
        .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 Kakao Access Token"));

    if (token.getAccessTokenExpiresAt() != null &&
        token.getAccessTokenExpiresAt().isBefore(Instant.now())) {
      throw new IllegalStateException("액세스 토큰이 만료되었습니다.");
    }

    return token.getMember();
  }

}
