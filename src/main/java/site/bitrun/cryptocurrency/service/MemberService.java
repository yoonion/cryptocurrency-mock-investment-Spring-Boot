package site.bitrun.cryptocurrency.service;

import jakarta.servlet.http.HttpServletRequest;
import site.bitrun.cryptocurrency.domain.Member;
import site.bitrun.cryptocurrency.dto.MemberRegisterForm;

public interface MemberService {

    void memberRegister(MemberRegisterForm form);

    boolean isMemberDuplicate(String email);

    Member memberLogin(String email, String password, HttpServletRequest request);

    void memberLogout(HttpServletRequest request);

    Member getMemberInfo(long memberId);
}
