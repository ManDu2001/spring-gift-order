package gift.exception;

import org.springframework.http.HttpStatus;

public class EmailAlreadyRegisteredException extends CustomException {
  public EmailAlreadyRegisteredException(String message) {
    super(message);
  }

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.CONFLICT;
  }

  @Override
  public String getTitle() {
    return "Email already registered";
  }

  @Override
  public String getType() {
    return "/api/members/email-already-registered";
  }
}
