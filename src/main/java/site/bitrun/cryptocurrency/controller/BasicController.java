package site.bitrun.cryptocurrency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import site.bitrun.cryptocurrency.domain.Member;
import site.bitrun.cryptocurrency.dto.MemberRegisterForm;
import site.bitrun.cryptocurrency.repository.MemberRepository;
import site.bitrun.cryptocurrency.service.MemberService;

@Controller
public class BasicController {

    private final MemberService memberService;

    @Autowired
    public BasicController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 메인 페이지
    @GetMapping("/")
    public String main() {
        return "index";
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

    // Chart TEST
    @GetMapping("/chart")
    public String viewChart() {
        return "chart";
    }
}
