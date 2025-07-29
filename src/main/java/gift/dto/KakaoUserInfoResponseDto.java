package gift.dto;

public record KakaoUserInfoResponseDto(
    Long id,
    KakaoAccount kakao_account
) {
  public record KakaoAccount(String email) {}

  public String email() {
    return kakao_account != null ? kakao_account.email() : null;
  }
}
