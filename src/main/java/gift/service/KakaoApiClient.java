package gift.service;

import gift.dto.KakaoOAuthResponseDto;
import gift.dto.KakaoUserInfoResponseDto;

public interface KakaoApiClientService {

  KakaoOAuthResponseDto getAccessToken(String authorizationCode);

  KakaoUserInfoResponseDto getUserInfo(String accessToken);
}
