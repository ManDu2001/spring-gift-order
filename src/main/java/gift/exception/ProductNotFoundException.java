package gift.exception;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends CustomException {
  public ProductNotFoundException(Long id) {
    super("해당 ID = " + id + " 의 상품이 존재하지 않습니다.");
  }

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.NOT_FOUND;
  }

  @Override
  public String getTitle() {
    return "Product Not Found";
  }

  @Override
  public String getType() {
    return "/api/products/product-not-found";
  }
}
