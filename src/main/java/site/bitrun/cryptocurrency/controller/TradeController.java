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
import site.bitrun.cryptocurrency.session.SessionConst;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TradeController {

    private final UpbitService upbitService;
    private final HoldCryptoService holdCryptoService;

    @Autowired
    public TradeController(UpbitService upbitService, HoldCryptoService holdCryptoService) {

        this.upbitService = upbitService;
        this.holdCryptoService = holdCryptoService;
    }

    // 거래소 view
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

    // 매수
    @PostMapping("/trade/order")
    public String cryptoBuy(@Validated @ModelAttribute BuyCryptoForm buyCryptoForm, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "trade/order";
        }

        String tradeType = buyCryptoForm.getTradeType(); // buy(매수) OR sell(매도)

        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (tradeType.equals("buy")) { // 매수
            holdCryptoService.buyCrypto(loginMember.getId(), buyCryptoForm.getBuyMarketCode(), buyCryptoForm.getBuyKrw());
        } else if (tradeType.equals("sell")) { // 매도

        }

        return "redirect:/trade/order";
    }

    // 보유자산 view
    @GetMapping("/trade/hold/crypto")
    public String viewHoldCryptoPage(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        List<HoldCryptoDto> holdCryptoList = holdCryptoService.getHoldCryptoList(loginMember.getId());
        model.addAttribute("holdCryptoList", holdCryptoList);

        // 보유한 암호화폐 upbit websocket 요청 json 부분 - 암호화폐 list json 요청
        List<String> marketListString = new ArrayList<>();
        for (HoldCryptoDto holdCrypto : holdCryptoList) {
            marketListString.add(holdCrypto.getMarketCode());
        }
        model.addAttribute("marketListString", marketListString);

        return "trade/holdCrypto";
    }

}