package site.bitrun.cryptocurrency.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import site.bitrun.cryptocurrency.global.api.coinmarketcap.service.CryptoService;

@Slf4j
@Component
public class ApiScheduler {

     // cron 표현식
     // 초(0-59), 분(0-59), 시간(0-23), 일(1-31), 월(1-12), 요일(0-6) (0: 일, 1: 월, 2:화, 3:수, 4:목, 5:금, 6:토)

    private final CryptoService cryptoService;

    @Autowired
    public ApiScheduler(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    // 코인마켓캡 API - 시가총액 top 100 저장 (1시간 마다 실행)
    @Scheduled(cron = "0 0 0/1 * * *", zone = "Asia/Seoul")
    public void saveCoinMarketCapCryptoRank() {
        log.info("saveCoinMarketCapCryptoRank() -> 코인마켓캡 시가총액 Top100 스케쥴러 실행");
        cryptoService.saveCryptoRankList();
    }

}
