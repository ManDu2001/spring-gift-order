package gift.exception;

import org.springframework.http.HttpStatus;

public class InvalidOptionQuantityException extends CustomException {
  public InvalidOptionQuantityException(int currentQuantity, int subtractAmount) {
    super("차감할 수 없는 수량입니다. 현재 수량: " + currentQuantity + ", 차감 시도 수량: " + subtractAmount +
        ". 차감할 경우 옵션의 남은 개수가 음수일 수 없습니다");
  }

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.BAD_REQUEST;
  }

  @Override
  public String getTitle() {
    return "Invalid Option Quantity";
  }

  @Override
  public String getType() {
    return "localhost:8080/api/options/invalid-quantity";
  }
}
