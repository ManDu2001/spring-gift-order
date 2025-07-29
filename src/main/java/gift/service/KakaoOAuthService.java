package gift.service;

import gift.domain.Member;
import gift.dto.KakaoOAuthResponseDto;
import gift.dto.KakaoUserInfoResponseDto;
import gift.dto.LoginResponseDto;

public interface KakaoOAuthService {
  KakaoOAuthResponseDto getAccessToken(String authorizationCode);

  KakaoUserInfoResponseDto getUserInfo(String accessToken);

  LoginResponseDto registerOrLogin(String authorizationCode);

  Member findMemberByAccessToken(String accessToken);
}
