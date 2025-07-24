package gift.exception;

import org.springframework.http.HttpStatus;

public class PasswordMismatchException extends CustomException {
  public PasswordMismatchException(String message) {
    super(message);
  }

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.UNAUTHORIZED;
  }

  @Override
  public String getTitle() {
    return "Password Mismatch";
  }

  @Override
  public String getType() {
    return "localhost:8080/api/members/password-mismatch";
  }
}
