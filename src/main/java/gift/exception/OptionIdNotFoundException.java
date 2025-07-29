package gift.exception;

import org.springframework.http.HttpStatus;

public class OptionIdNotFoundException extends CustomException {
  public OptionIdNotFoundException(Long optionId) {
    super("옵션 ID = " + optionId + "에 대한 옵션이 존재하지 않습니다.");
  }

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.NOT_FOUND;
  }

  @Override
  public String getTitle() {
    return "Options Id Not Found";
  }

  @Override
  public String getType() {
    return "/api/products/options-id-not-found";
  }
}

