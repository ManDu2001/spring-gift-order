package gift.security;

import gift.domain.Member;
import gift.service.KakaoOAuthService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginKakaoMemberArgumentResolver implements HandlerMethodArgumentResolver {

  private final KakaoOAuthService kakaoOAuthService;

  public LoginKakaoMemberArgumentResolver(KakaoOAuthService kakaoOAuthService) {
    this.kakaoOAuthService = kakaoOAuthService;
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(LoginKakaoMember.class)
        && parameter.getParameterType().equals(Member.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory) {

    String authHeader = webRequest.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new IllegalStateException("Authorization 헤더가 없거나 잘못되었습니다.");
    }

    String accessToken = authHeader.substring(7);
    return kakaoOAuthService.findMemberByAccessToken(accessToken);
  }
}
