package gift.service;

public interface KakaoOAuthService {
  String getAccessToken(String authorizationCode);
}
