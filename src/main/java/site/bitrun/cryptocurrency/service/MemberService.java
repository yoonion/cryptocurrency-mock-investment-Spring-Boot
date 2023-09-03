package site.bitrun.cryptocurrency.service;

import jakarta.servlet.http.HttpServletRequest;
import site.bitrun.cryptocurrency.domain.Member;

public interface MemberService {

    void memberRegister(Member member);

    Member memberLogin(String email, String password);

    void memberLogout(HttpServletRequest request);
}
