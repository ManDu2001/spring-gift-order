package gift.service;

import gift.dto.KakaoOAuthResponseDto;

public interface KakaoOAuthService {
  KakaoOAuthResponseDto getAccessToken(String authorizationCode);
}
