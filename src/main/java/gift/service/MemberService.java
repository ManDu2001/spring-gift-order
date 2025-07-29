package gift.service;

import gift.domain.Member;
import gift.dto.KakaoOAuthResponseDto;
import gift.dto.LoginResponseDto;
import gift.dto.MemberInfoResponseDto;
import gift.dto.MemberLoginRequestDto;
import gift.dto.MemberLoginResponseDto;
import java.util.List;

public interface MemberService {

  MemberLoginResponseDto register(MemberLoginRequestDto memberLoginRequestDto);

  MemberLoginResponseDto login(MemberLoginRequestDto memberLoginRequestDto);

  MemberInfoResponseDto searchMemberById(Long id);
  List<MemberInfoResponseDto> searchAllMembers();

  MemberInfoResponseDto updateMember(Long id, MemberLoginRequestDto memberLoginRequestDto);

  void deleteMember(Long id);

  public Member registerOrLoginByKakao(String email, KakaoOAuthResponseDto tokenDto);
}
