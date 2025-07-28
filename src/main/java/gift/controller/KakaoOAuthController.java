package gift.controller;

import gift.dto.KakaoOAuthResponseDto;
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
  public ResponseEntity<KakaoOAuthResponseDto> receiveCode(@RequestParam("code") String code) {
    KakaoOAuthResponseDto dto = kakaoOAuthService.getAccessToken(code);
    return ResponseEntity.ok(dto);
  }
}
