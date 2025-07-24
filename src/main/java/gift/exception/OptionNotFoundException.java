package gift.exception;

import org.springframework.http.HttpStatus;

public class OptionNotFoundException extends CustomException {
  public OptionNotFoundException(Long productId, String optionName) {
    super("상품 ID = " + productId + " 에 해당하는 옵션 이름 '" + optionName + "' 이(가) 존재하지 않습니다.");
  }

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.NOT_FOUND;
  }

  @Override
  public String getTitle() {
    return "Options Not Found";
  }

  @Override
  public String getType() {
    return "localhost:8080/api/products/options-not-found";
  }
}
