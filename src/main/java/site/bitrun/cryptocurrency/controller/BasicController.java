package site.bitrun.cryptocurrency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import site.bitrun.cryptocurrency.domain.CryptoRank;
import site.bitrun.cryptocurrency.domain.Member;
import site.bitrun.cryptocurrency.dto.CryptoRankDto;
import site.bitrun.cryptocurrency.dto.MemberRegisterForm;
import site.bitrun.cryptocurrency.service.CryptoService;
import site.bitrun.cryptocurrency.service.MemberService;

import java.util.List;
import java.util.Map;

@Controller
public class BasicController {

    private final MemberService memberService;
    private final CryptoService cryptoService;

    @Autowired
    public BasicController(MemberService memberService, CryptoService cryptoService) {
        this.memberService = memberService;
        this.cryptoService = cryptoService;
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

    // Chart TEST
    @GetMapping("/chart")
    public String viewChart() {
        return "chart";
    }
}
