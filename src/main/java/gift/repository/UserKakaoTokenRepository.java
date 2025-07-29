package gift.repository;

import gift.domain.UserKakaoToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserKakaoTokenRepository extends JpaRepository<UserKakaoToken, Long> {
  Optional<UserKakaoToken> findByMemberEmail(String email);

  Optional<UserKakaoToken> findByAccessToken(String accessToken);
}
