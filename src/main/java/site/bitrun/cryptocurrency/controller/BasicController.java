package site.bitrun.cryptocurrency.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import site.bitrun.cryptocurrency.domain.CryptoRank;
import site.bitrun.cryptocurrency.domain.Member;
import site.bitrun.cryptocurrency.dto.MemberLoginForm;
import site.bitrun.cryptocurrency.dto.MemberRegisterForm;
import site.bitrun.cryptocurrency.service.CryptoService;
import site.bitrun.cryptocurrency.service.MemberService;
import site.bitrun.cryptocurrency.session.SessionConst;

import java.util.List;


@Controller
public class BasicController {

    private final MemberService memberService;
    private final CryptoService cryptoService;

    @Autowired
    public BasicController(MemberService memberService, CryptoService cryptoService) {
        this.memberService = memberService;
        this.cryptoService = cryptoService;
    }

    // Chart TEST
    @GetMapping("/chart")
    public String viewChart() {
        return "chart";
    }

    // 메인 페이지
    @GetMapping("/")
    public String main(Model model) {
        List<CryptoRank> cryptoRankList = cryptoService.getCryptoRankList();
        model.addAttribute("cryptoRankList", cryptoRankList);

        return "index";
    }

    // 코인마켓캡 api 호출해 DB 갱신 - 임시
    @GetMapping("/crypto/rank/api/save")
    public String cryptoRankApi() {
        cryptoService.saveCryptoRankList();
        return "redirect:/";
    }

    // 회원가입 view
    @GetMapping("/member/register")
    public String memberRegisterForm(@ModelAttribute MemberRegisterForm memberRegisterForm) {
        return "memberRegisterForm";
    }

    // 회원가입
    @PostMapping("/member/register")
    public String memberRegister(@Validated @ModelAttribute MemberRegisterForm memberRegisterForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "memberRegisterForm";
        }

        // 비밀번호 확인
        if (!memberRegisterForm.getPassword().equals(memberRegisterForm.getPassword2())) {
            bindingResult.rejectValue("password2", "differentPassword", "패스워드가 일치하지 않습니다.");
            return "memberRegisterForm";
        }

        Member newMember = new Member(memberRegisterForm.getUsername(), memberRegisterForm.getEmail(), memberRegisterForm.getPassword());
        memberService.memberRegister(newMember);

        return "redirect:/";
    }

    // 로그인 view
    @GetMapping("/member/login")
    public String memberLoginForm(@ModelAttribute MemberLoginForm memberLoginForm) {
        return "memberLoginForm";
    }

    // 로그인 처리
    @PostMapping("/member/login")
    public String memberLogin(@Validated @ModelAttribute MemberLoginForm memberLoginForm, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "memberLoginForm";
        }

        Member loginMember = memberService.memberLogin(memberLoginForm.getEmail(), memberLoginForm.getPassword());

        // 로그인 실패시
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다."); // global error
            return "memberLoginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:/";
    }

    // 로그아웃
    @GetMapping("/member/logout")
    public String memberLogout(HttpServletRequest request) {
        memberService.memberLogout(request);

        return "redirect:/";
    }

}
