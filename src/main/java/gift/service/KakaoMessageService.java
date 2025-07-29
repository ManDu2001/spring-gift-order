package gift.service;

import gift.domain.Order;

public interface KakaoMessageService {
  void sendOrderMessage(Long memberId, Order order);
}
