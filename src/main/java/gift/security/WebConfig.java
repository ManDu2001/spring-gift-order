package gift.security;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  private final LoginMemberIdArgumentResolver loginMemberIdArgumentResolver;

  private final LoginKakaoMemberArgumentResolver loginKakaoMemberArgumentResolver;

  public WebConfig(LoginMemberIdArgumentResolver loginMemberIdArgumentResolver
  , LoginKakaoMemberArgumentResolver loginKakaoMemberArgumentResolver) {
    this.loginMemberIdArgumentResolver = loginMemberIdArgumentResolver;
    this.loginKakaoMemberArgumentResolver = loginKakaoMemberArgumentResolver;
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(loginMemberIdArgumentResolver);
    resolvers.add(loginKakaoMemberArgumentResolver);
  }
}
