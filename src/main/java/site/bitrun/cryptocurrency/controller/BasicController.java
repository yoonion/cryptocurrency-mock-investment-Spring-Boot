package site.bitrun.cryptocurrency.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.bitrun.cryptocurrency.global.api.coinmarketcap.domain.CryptoRank;
import site.bitrun.cryptocurrency.domain.Member;
import site.bitrun.cryptocurrency.dto.MemberLoginForm;
import site.bitrun.cryptocurrency.dto.MemberRegisterForm;
import site.bitrun.cryptocurrency.global.api.coinmarketcap.service.CryptoService;
import site.bitrun.cryptocurrency.global.api.upbit.domain.UpbitMarket;
import site.bitrun.cryptocurrency.global.api.upbit.service.UpbitService;
import site.bitrun.cryptocurrency.service.HoldCryptoService;
import site.bitrun.cryptocurrency.service.MemberService;
import site.bitrun.cryptocurrency.session.SessionConst;

import java.util.ArrayList;
import java.util.List;


@Controller
public class BasicController {

    private final MemberService memberService;
    private final CryptoService cryptoService;
    private final UpbitService upbitService;
    private final HoldCryptoService holdCryptoService;

    @Autowired
    public BasicController(MemberService memberService, CryptoService cryptoService, UpbitService upbitService, HoldCryptoService holdCryptoService) {
        this.memberService = memberService;
        this.cryptoService = cryptoService;
        this.upbitService = upbitService;
        this.holdCryptoService = holdCryptoService;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////// API DB SAVE /////////////////////////////////
    /////////////// 코인마켓캡 api 호출해 DB 저장 - 임시 .. //////////////////
    @GetMapping("/crypto/rank/api/save")
    public String cryptoRankApi() {
        cryptoService.saveCryptoRankList();
        return "redirect:/";
    }

    ///////////////// 업비트 api 호출 - 거래가능 마켓 목록 DB 저장 - 임시 .. ///////////////
    @GetMapping("/upbit/api/save")
    public String upbitMarketApi() {
        upbitService.saveUpbitMarket();
        return "redirect:/";
    }
    ////////////////////////// API DB SAVE //////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////

    // 메인 페이지
    @GetMapping("/")
    public String main(Model model) {
        List<CryptoRank> cryptoRankList = cryptoService.getCryptoRankList();
        model.addAttribute("cryptoRankList", cryptoRankList);

        return "index";
    }

    // 거래소
    @GetMapping("/trade/order")
    public String viewOrderPage(Model model) {

        // 오른쪽 side nav 를 위한 전체 리스트
        List<UpbitMarket> upbitMarketList = upbitService.getUpbitMarketList();
        model.addAttribute(upbitMarketList);

        // upbit websocket 요청 json 부분 - 암호화폐 list json 요청에 넣어줄 것임
        List<String> marketListString = new ArrayList<>();
        for (UpbitMarket upbitMarket : upbitMarketList) {
            marketListString.add(upbitMarket.getMarket());
        }
        model.addAttribute("marketListString", marketListString);

        return "trade/order";
    }

    // 회원가입 view
    @GetMapping("/member/register")
    public String memberRegisterForm(@ModelAttribute MemberRegisterForm memberRegisterForm) {
        return "memberRegisterForm";
    }

    // 회원가입
    @PostMapping("/member/register")
    public String memberRegister(@Validated @ModelAttribute MemberRegisterForm memberRegisterForm, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "memberRegisterForm";
        }

        // 비밀번호 확인
        if (!memberRegisterForm.getPassword().equals(memberRegisterForm.getPassword2())) {
            bindingResult.rejectValue("password2", "differentPassword", "패스워드가 일치하지 않습니다.");
            return "memberRegisterForm";
        }

        // 중복 체크
        // true 일 경우 중복
        if (memberService.memberCheckDuplicate(memberRegisterForm.getEmail())) {
            bindingResult.reject("emailDuplicate", "이미 존재하는 회원입니다."); // global error
            return "memberRegisterForm";
        }

        Member newMember = new Member(memberRegisterForm.getUsername(), memberRegisterForm.getEmail(), memberRegisterForm.getPassword(), 10000000);
        memberService.memberRegister(newMember);

        // 성공했다면 로그인까지 처리한다.
        memberService.memberLogin(memberRegisterForm.getEmail(), memberRegisterForm.getPassword(), request);

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

        Member loginMember = memberService.memberLogin(memberLoginForm.getEmail(), memberLoginForm.getPassword(), request);

        // 로그인 실패시
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다."); // global error
            return "memberLoginForm";
        }

        return "redirect:/";
    }

    // 로그아웃
    @GetMapping("/member/logout")
    public String memberLogout(HttpServletRequest request) {
        memberService.memberLogout(request);

        return "redirect:/";
    }

    // 매수
    @PostMapping("/crypto/buy")
    public String cryptoBuy(
            @RequestParam(name = "buy_market_code") String buyMarketCode,
            @RequestParam(name = "buy_price") long buyKrw,
            HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        holdCryptoService.buyCrypto(loginMember.getId(), buyMarketCode, buyKrw);

        return "redirect:/trade/order";
    }

    // 매도
    /*@PostMapping("/crypto/sell")
    @ResponseBody
    public String cryptoSell(
            @RequestParam(name = "buy_market_code") String buyMarketCode,
            @RequestParam(name = "buy_price") String buyPrice) {
        System.out.println("buyMarketCode = " + buyMarketCode);
        System.out.println("buyPrice = " + buyPrice);

        return "ok";
    }*/
}
