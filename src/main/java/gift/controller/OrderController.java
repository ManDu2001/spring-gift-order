package gift.controller;

import gift.domain.Member;
import gift.dto.OrderRequestDto;
import gift.dto.OrderResponseDto;
import gift.security.LoginKakaoMember;
import gift.service.OrderService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  public ResponseEntity<OrderResponseDto> createOrder(
      @LoginKakaoMember Member member,
      @Valid @RequestBody OrderRequestDto dto) {
    OrderResponseDto response = orderService.createOrder(member, dto);
    URI location = URI.create("/api/orders/" + response.id());
    return ResponseEntity.created(location).body(response);
  }
}
