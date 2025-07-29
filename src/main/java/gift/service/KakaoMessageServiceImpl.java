package gift.service;

import gift.domain.Order;
import gift.domain.UserKakaoToken;
import gift.repository.UserKakaoTokenRepository;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriUtils;

@Service
public class KakaoMessageServiceImpl implements KakaoMessageService {

  private final RestClient restClient;

  private final UserKakaoTokenRepository userKakaoTokenRepository;

  public KakaoMessageServiceImpl(RestClient.Builder restClientBuilder, UserKakaoTokenRepository userKakaoTokenRepository) {
    this.restClient = restClientBuilder
        .baseUrl("https://kapi.kakao.com")
        .build();
    this.userKakaoTokenRepository = userKakaoTokenRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public void sendOrderMessage(Long memberId, Order order) {
    String accessToken = getAccessToken(memberId);

    String templateJson = createTemplateJson(order);
    String formBody = "template_object=" + UriUtils.encode(templateJson, StandardCharsets.UTF_8);

    restClient.post()
        .uri("/v2/api/talk/memo/default/send")
        .header("Authorization", "Bearer " + accessToken)
        .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8")
        .body(formBody)
        .retrieve()
        .toBodilessEntity();
  }

  private String createTemplateJson(Order order) {
    return "{"
        + "\"object_type\":\"text\","
        + "\"text\":\"주문이 완료되었습니다. 옵션ID: " + order.getOptionId()
        + ", 수량: " + order.getQuantity()
        + ", 메세지: " + order.getMessage() + "\","
        + "\"link\":{\"web_url\":\"https://your-service-url.com/orders\"},"
        + "\"button_title\":\"주문 내역 확인\""
        + "}";
  }
  @Transactional(readOnly = true)
  private String getAccessToken(Long memberId) {
    UserKakaoToken token = userKakaoTokenRepository.findById(memberId)
        .orElseThrow(() -> new IllegalStateException("해당 사용자의 카카오 토큰이 존재하지 않습니다."));

    if (token.getAccessTokenExpiresAt() != null && token.getAccessTokenExpiresAt().isBefore(Instant.now())) {
      throw new IllegalStateException("액세스 토큰이 만료되었습니다. 리프레시 필요.");
    }

    return token.getAccessToken();
  }
}
