package site.bitrun.cryptocurrency.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import site.bitrun.cryptocurrency.domain.HoldCrypto;
import site.bitrun.cryptocurrency.domain.Member;
import site.bitrun.cryptocurrency.global.api.upbit.domain.UpbitMarket;
import site.bitrun.cryptocurrency.global.api.upbit.dto.UpbitTradePriceDto;
import site.bitrun.cryptocurrency.global.api.upbit.repository.UpbitRepository;
import site.bitrun.cryptocurrency.repository.HoldCryptoRepository;
import site.bitrun.cryptocurrency.repository.MemberRepository;

@Service
public class HoldCryptoServiceImpl implements HoldCryptoService {

    private final UpbitRepository upbitRepository;
    private final HoldCryptoRepository holdCryptoRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public HoldCryptoServiceImpl(UpbitRepository upbitRepository, HoldCryptoRepository holdCryptoRepository, MemberRepository memberRepository) {
        this.upbitRepository = upbitRepository;
        this.holdCryptoRepository = holdCryptoRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public void buyCrypto(long memberId, String marketCode, long buyKrw) {

        Member findMember = memberRepository.findById(memberId);
        UpbitMarket findCrypto = upbitRepository.findByMarket(marketCode);

        /**
         * 업비트 API > 암호화폐 현재가 가져오기 - trade_price
         * 비트코인 - https://api.upbit.com/v1/ticker?markets=KRW-BTC
         */
        String requestUrl = "https://api.upbit.com/v1/ticker?markets=" + marketCode;

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(requestUrl, String.class);

        ObjectMapper objectMapper = new ObjectMapper(); // Jackson lib
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 필드로 선언한 데이터만 파싱

        // 현재 암호화폐의 가격 매핑해서 가져온다
        UpbitTradePriceDto[] tradePrice = new UpbitTradePriceDto[0];
        try {
            tradePrice = objectMapper.readValue(response, UpbitTradePriceDto[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // 매수 할 암호화폐 개수 (매수요청한 금액(KRW) / 암호화폐 가격 -> 10,000원 짜리 암호화폐를 5,000원 구매하면 0.5개 구매한 것)
        double nowTradePrice = Double.parseDouble(tradePrice[0].getTradePrice());
        String buyCryptoCountStr = String.format("%.8f", buyKrw / nowTradePrice); // 소수점 8째 자리 수 까지만
        double buyCryptoCount = Double.parseDouble(buyCryptoCountStr);

        // 해당 암호화폐를 보유 중 인지 select 해본다
        HoldCrypto findHoldCrypto = holdCryptoRepository.findByMemberIdAndUpbitMarketId(findMember.getId(), findCrypto.getId());

        // 해당 암호화폐를 처음 매수하는 경우, 해당 암호화폐 정보를 insert 시킨다.
        if (findHoldCrypto == null) {
            HoldCrypto holdCrypto = new HoldCrypto(findMember, findCrypto, buyCryptoCount, nowTradePrice, buyKrw);
            holdCryptoRepository.save(holdCrypto);
        } else {
            // 해당 암호화폐를 이미 보유 중 이라면, 기존 매수 정보에 계산해서 update 시킨다.
            // 새로운 평단가 구하는 공식 - (n차 매수 가격(암호화폐 가격) * 수량 + (n+1)차 매수 가격(KRW)) / 전체 수량
            double beforeBuyAverage = findHoldCrypto.getBuyAverage(); // 이전 평단가
            double beforeBuyCryptoCount = findHoldCrypto.getBuyCryptoCount(); // 이전 보유중인 암호화폐 개수
            long totalBuyKrw = findHoldCrypto.getBuyTotalKrw() + buyKrw; // 총 매수 금액(KRW)

            double totalBuyCryptoCount = findHoldCrypto.getBuyCryptoCount() + buyCryptoCount; // 총 매수 암호화폐 개수
            double newBuyAverage = (beforeBuyAverage * beforeBuyCryptoCount + buyKrw) / totalBuyCryptoCount; // 새로운 평단가

            // 매수 정보 업데이트
            findHoldCrypto.setBuyCryptoCount(totalBuyCryptoCount);
            findHoldCrypto.setBuyAverage(newBuyAverage);
            findHoldCrypto.setBuyTotalKrw(totalBuyKrw);
        }

        // 매수 후, 총 보유자산 업데이트 (보유자산(KRW) - 매수한 금액(KRW))
        long currentAsset = findMember.getAsset();
        long afterAsset = currentAsset - buyKrw;
        findMember.setAsset(afterAsset);
    }

}
