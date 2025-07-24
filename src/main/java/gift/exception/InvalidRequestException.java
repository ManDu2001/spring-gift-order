package gift.exception;

import org.springframework.http.HttpStatus;

public class InvalidRequestException extends CustomException {
  public InvalidRequestException(String message) {
    super(message);
  }

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.BAD_REQUEST;
  }

  @Override
  public String getTitle() {
    return "Invalid request";
  }

  @Override
  public String getType() {
    return "localhost:8080/api/products/invalid-request";
  }
}
