package gift.exception;

import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends CustomException {
  public MemberNotFoundException(String message) {
    super(message);
  }

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.NOT_FOUND;
  }

  @Override
  public String getTitle() {
    return "Member Not Found";
  }

  @Override
  public String getType() {
    return "/api/members/member-not-found";
  }
}
