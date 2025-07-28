package gift.exception;

import org.springframework.http.HttpStatus;

public class WishListItemNotFoundException extends CustomException {
  public WishListItemNotFoundException(Long memberId, Long productId) {
    super("memberId = " + memberId + ", productId = " + productId + " 에 해당하는 위시리스트 항목이 존재하지 않습니다.");
  }

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.NOT_FOUND;
  }

  @Override
  public String getTitle() {
    return "WishList Item Not Found";
  }

  @Override
  public String getType() {
    return "/api/wishlist/item-not-found";
  }
}
