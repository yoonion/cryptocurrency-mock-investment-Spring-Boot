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
import site.bitrun.cryptocurrency.domain.HoldCrypto;
import site.bitrun.cryptocurrency.domain.Member;
import site.bitrun.cryptocurrency.dto.BuyCryptoForm;
import site.bitrun.cryptocurrency.dto.HoldCryptoDto;
import site.bitrun.cryptocurrency.global.api.upbit.domain.UpbitMarket;
import site.bitrun.cryptocurrency.global.api.upbit.service.UpbitService;
import site.bitrun.cryptocurrency.service.HoldCryptoService;
import site.bitrun.cryptocurrency.service.MemberService;
import site.bitrun.cryptocurrency.session.SessionConst;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TradeController {

    private final UpbitService upbitService;
    private final HoldCryptoService holdCryptoService;
    private final MemberService memberService;

    @Autowired
    public TradeController(UpbitService upbitService, HoldCryptoService holdCryptoService, MemberService memberService) {
        this.upbitService = upbitService;
        this.holdCryptoService = holdCryptoService;
        this.memberService = memberService;
    }

    // 거래소 view
    @GetMapping("/trade/order")
    public String viewOrderPage(Model model, HttpServletRequest request) {

        // 오른쪽 side nav 를 위한 전체 리스트
        List<UpbitMarket> upbitMarketList = upbitService.getUpbitMarketList();
        model.addAttribute(upbitMarketList);

        // 보유자산(매수가능자산 KRW)
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (loginMember != null) {
            Member memberInfo = memberService.getMemberInfo(loginMember.getId());
            long memberAsset = memberInfo.getAsset();
            model.addAttribute("memberAsset", memberAsset);
        }

        // upbit websocket 요청 json 부분 - 암호화폐 list json 요청에 넣어줄 것임
        List<String> marketListString = new ArrayList<>();
        for (UpbitMarket upbitMarket : upbitMarketList) {
            marketListString.add(upbitMarket.getMarket());
        }
        model.addAttribute("marketListString", marketListString);

        return "trade/order";
    }

    // 매수 & 매도
    @PostMapping("/trade/order")
    public String cryptoBuy(@Validated @ModelAttribute BuyCryptoForm buyCryptoForm, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "trade/order";
        }

        String tradeType = buyCryptoForm.getTradeType(); // buy(매수) OR sell(매도)
        System.out.println("tradeType = " + tradeType);

        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (tradeType.equals("buy")) { // 매수
            long buyKrw = Long.parseLong(buyCryptoForm.getBuyKrw().replaceAll(",", "")); // 금액에 ',' 제거
            holdCryptoService.buyCrypto(loginMember.getId(), buyCryptoForm.getBuyMarketCode(), buyKrw);
        } else if (tradeType.equals("sell")) { // 매도
//            System.out.println("매도 부분 입니다");
        }

        return "redirect:/trade/order";
    }

    // 보유자산 view
    @GetMapping("/trade/hold/crypto")
    public String viewHoldCryptoPage(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        // 보유 암호화폐 list
        List<HoldCryptoDto> holdCryptoList = holdCryptoService.getHoldCryptoList(loginMember.getId());
        model.addAttribute("holdCryptoList", holdCryptoList);

        // 보유자산(매수가능자산 KRW)
        Member memberInfo = memberService.getMemberInfo(loginMember.getId());
        long memberAsset = memberInfo.getAsset();
        model.addAttribute("memberAsset", memberAsset);

        // 총매수금액 (KRW)
        long totalBuyKrw = 0;
        for (HoldCryptoDto holdCryptoDto : holdCryptoList) {
            totalBuyKrw += holdCryptoDto.getBuyTotalKrw();
        }
        model.addAttribute("totalBuyKrw", totalBuyKrw);

        // 보유한 암호화폐 upbit websocket 요청 json 부분 - 암호화폐 list json 요청
        List<String> marketArrayList = new ArrayList<>();
        for (HoldCryptoDto holdCrypto : holdCryptoList) {
            marketArrayList.add(holdCrypto.getMarketCode());
        }
        model.addAttribute("marketArrayList", marketArrayList);

        return "trade/holdCrypto";
    }

}
