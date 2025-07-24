package gift.controller;

import gift.service.KakaoOAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KakaoOAuthController {

  private final KakaoOAuthService kakaoOAuthService;

  public KakaoOAuthController(KakaoOAuthService kakaoOAuthService) {
    this.kakaoOAuthService = kakaoOAuthService;
  }

  @GetMapping("/")
  public ResponseEntity<String> receiveCode(@RequestParam("code") String code) {
    String accessToken = kakaoOAuthService.getAccessToken(code);
    return ResponseEntity.ok("Access Token: " + accessToken);
  }
}
