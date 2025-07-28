package gift.service;

import com.fasterxml.jackson.databind.JsonNode;
import gift.config.KakaoProperties;
import gift.dto.KakaoOAuthResponseDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Service
public class KakaoOAuthServiceImpl implements KakaoOAuthService {

  private final KakaoProperties kakaoProperties;
  private final RestClient restClient;

  public KakaoOAuthServiceImpl(KakaoProperties kakaoProperties) {
    this.kakaoProperties = kakaoProperties;
    this.restClient = RestClient.builder()
        .baseUrl("https://kauth.kakao.com")
        .build();
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
}
