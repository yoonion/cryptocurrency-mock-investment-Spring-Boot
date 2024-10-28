package site.bitrun.cryptocurrency.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.bitrun.cryptocurrency.constants.AppConst;
import site.bitrun.cryptocurrency.domain.Member;
import site.bitrun.cryptocurrency.dto.MemberRegisterForm;
import site.bitrun.cryptocurrency.repository.MemberRepository;
import site.bitrun.cryptocurrency.constants.session.SessionConst;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Override
    public void memberRegister(MemberRegisterForm form) {
        String encodePassword = passwordEncoder.encode(form.getPassword()); // 비밀번호 암호화
        Member newMember = new Member(form.getUsername(), form.getEmail(), encodePassword, AppConst.MEMBER_STARTING_ASSET);

        memberRepository.save(newMember);
    }

    // 로그인
    @Override
    public Member memberLogin(String email, String password, HttpServletRequest request) {
        Optional<Member> findMember = memberRepository.findByEmail(email);

        if (findMember.isEmpty()) {
            return null;
        }

        Member member = findMember.get();
        if ( passwordEncoder.matches(password, member.getPassword()) ) {
            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, member);

            return member;
        } else {
            return null;
        }

    }

    // 로그아웃
    @Override
    public void memberLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
    }

    // 회원 중복체크
    @Override
    public boolean isMemberDuplicate(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    // 회원 정보 가져오기
    @Override
    public Member getMemberInfo(long memberId) {
        return memberRepository.findById(memberId);
    }

}
