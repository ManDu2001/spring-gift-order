package gift.controller;

import gift.dto.LoginResponseDto;
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
  public ResponseEntity<LoginResponseDto> receiveCode(@RequestParam("code") String code) {
    LoginResponseDto loginResponseDto = kakaoOAuthService.registerOrLogin(code);
    return ResponseEntity.ok(loginResponseDto);
  }
}
