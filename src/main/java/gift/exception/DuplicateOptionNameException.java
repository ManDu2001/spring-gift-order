package gift.exception;

import org.springframework.http.HttpStatus;

public class DuplicateOptionNameException extends CustomException {
  public DuplicateOptionNameException(Long productId, String optionName) {
    super("상품 ID = " + productId + " 에 이미 '" + optionName + "' 이름의 옵션이 존재합니다.");
  }

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.CONFLICT;
  }

  @Override
  public String getTitle() {
    return "Duplicate Option Name";
  }

  @Override
  public String getType() {
    return "/api/products/duplicate-option-name";
  }
}
