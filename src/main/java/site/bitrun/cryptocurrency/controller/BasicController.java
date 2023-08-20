package site.bitrun.cryptocurrency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import site.bitrun.cryptocurrency.domain.Member;
import site.bitrun.cryptocurrency.repository.MemberRepository;
import site.bitrun.cryptocurrency.repository.MemoryMemberRepository;
import site.bitrun.cryptocurrency.service.MemberService;

import java.util.Map;

@Controller
public class BasicController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @Autowired
    public BasicController(MemberRepository memberRepository, MemberService memberService) {
        this.memberRepository = memberRepository;
        this.memberService = memberService;
    }

    // 메인 페이지
    @GetMapping("/")
    public String main() {
        return "index";
    }

    // 회원가입 view
    @GetMapping("/member/register")
    public String memberRegisterForm() {
        return "memberRegister";
    }

    // 회원가입
    @PostMapping("/member/register")
    public String memberRegister(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password) {

        Member member = new Member(1L, username, email, password);
        memberService.memberRegister(member);

        System.out.println("member = " + member.toString());
        Map<Long, Member> nowMember = MemoryMemberRepository.store;
        System.out.println("nowMember.get(1) = " + nowMember.get(1L));

        return "redirect:/";
    }
}
