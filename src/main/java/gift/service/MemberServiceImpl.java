package gift.service;

import gift.domain.Member;
import gift.domain.Role;
import gift.dto.MemberInfoResponseDto;
import gift.dto.MemberLoginRequestDto;
import gift.dto.MemberLoginResponseDto;
import gift.exception.EmailAlreadyRegisteredException;
import gift.exception.MemberNotFoundException;
import gift.exception.PasswordMismatchException;
import gift.repository.MemberRepository;
import gift.security.BcryptPasswordEncoder;
import gift.security.JwtProvider;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository;
  private final JwtProvider jwtProvider;
  private final BcryptPasswordEncoder passwordEncoder;

  public MemberServiceImpl(MemberRepository memberRepository, JwtProvider jwtProvider, BcryptPasswordEncoder passwordEncoder) {
    this.memberRepository = memberRepository;
    this.jwtProvider = jwtProvider;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public MemberLoginResponseDto register(MemberLoginRequestDto memberLoginRequestDto) {

    String email = memberLoginRequestDto.email();
    String password = memberLoginRequestDto.password();

    if (memberRepository.findByEmail(email).isPresent()) {
      throw new EmailAlreadyRegisteredException("이미 가입된 이메일입니다.");
    }

    String encodedPassword = passwordEncoder.encode(password);
    Member member = new Member(email, encodedPassword, Role.USER);
    Member saved = memberRepository.save(member);
    String token = jwtProvider.generateToken(saved);

    return new MemberLoginResponseDto(token);
  }

  @Override
  public MemberLoginResponseDto login(MemberLoginRequestDto memberLoginRequestDto) {

    String email = memberLoginRequestDto.email();
    String password = memberLoginRequestDto.password();

    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

    if (!passwordEncoder.matches(password, member.getPassword())) {
      throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
    }

    String token = jwtProvider.generateToken(member);

    return new MemberLoginResponseDto(token);
  }

  public MemberInfoResponseDto searchMemberById(Long id){
    Optional<Member> optionalMember = memberRepository.findById(id);

    Member member = optionalMember.orElseThrow(() ->
        new NoSuchElementException("해당 ID = " + id + " 의 회원이 존재하지 않습니다.")
    );

    return new MemberInfoResponseDto(member);
  }

  public List<MemberInfoResponseDto> searchAllMembers(){
    return memberRepository.findAll()
        .stream()
        .map(MemberInfoResponseDto::new)
        .collect(Collectors.toList());
  }

  public MemberInfoResponseDto updateMember(Long id, MemberLoginRequestDto memberLoginRequestDto){
    String email = memberLoginRequestDto.email();
    String password = memberLoginRequestDto.password();

    Optional<Member> opMember = memberRepository.findByEmail(email);
    if (opMember.isPresent()) {
      throw new EmailAlreadyRegisteredException("이미 가입된 이메일입니다.");
    }
    Member member = opMember.get();

    String encodedPassword = passwordEncoder.encode(password);
    member.update(email, encodedPassword, Role.USER);
    String token = jwtProvider.generateToken(member);

    return new MemberInfoResponseDto(member);
  }

  public void deleteMember(Long id){
    searchMemberById(id);
    memberRepository.deleteById(id);
  }
}
