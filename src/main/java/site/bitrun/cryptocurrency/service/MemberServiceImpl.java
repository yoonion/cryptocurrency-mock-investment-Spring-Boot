package site.bitrun.cryptocurrency.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.bitrun.cryptocurrency.domain.Member;
import site.bitrun.cryptocurrency.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원가입
    @Override
    public void memberRegister(Member member) {
        memberRepository.save(member);
    }

    // 로그인
    @Override
    public Member memberLogin(String email, String password) {
        Member findMember = memberRepository.findByEmail(email);

        if (findMember.getPassword().equals(password)) {
            return findMember; // 로그인 성공
        } else {
            return null; // 로그인 실패
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

}
