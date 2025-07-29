package gift.service;

import gift.domain.Member;
import gift.dto.OrderRequestDto;
import gift.dto.OrderResponseDto;

public interface OrderService {
  OrderResponseDto createOrder(Member member, OrderRequestDto dto);
}
