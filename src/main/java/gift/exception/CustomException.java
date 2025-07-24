package gift.exception;

import org.springframework.http.HttpStatus;

public abstract class CustomException extends RuntimeException {
  public CustomException(String message) {
    super(message);
  }

  public abstract HttpStatus getStatus();
  public abstract String getTitle();
  public abstract String getType(); // URI string
}
