package site.bitrun.cryptocurrency.controller.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import site.bitrun.cryptocurrency.global.api.upbit.domain.UpbitMarket;
import site.bitrun.cryptocurrency.global.api.upbit.service.UpbitService;

import java.util.List;

@RestController
public class ApiController {

    private final UpbitService upbitService;

    @Autowired
    public ApiController(UpbitService upbitService) {
        this.upbitService = upbitService;
    }

    // 거래소 개별 암호화폐 정보 API
    @GetMapping("/api/crypto/{code}")
    public UpbitMarketApiDto getCryptoInfo(@PathVariable("code") String code) {

        // 쿼리 파라미터로 해당하는 암호화폐의 정보를 1개 가져온다.
        UpbitMarket findUpbitCryptoOne = upbitService.getUpbitMarketOne(code);
        UpbitMarketApiDto result = new UpbitMarketApiDto(findUpbitCryptoOne.getMarket(), findUpbitCryptoOne.getKoreanName(), findUpbitCryptoOne.getEnglishName());

        return result;
    }

    static class UpbitMarketApiDto {
        String marketCode;
        String koreanName;
        String englishName;

        public UpbitMarketApiDto(String marketCode, String koreanName, String englishName) {
            this.marketCode = marketCode;
            this.koreanName = koreanName;
            this.englishName = englishName;
        }

        public String getMarketCode() {
            return marketCode;
        }

        public void setMarketCode(String marketCode) {
            this.marketCode = marketCode;
        }

        public String getKoreanName() {
            return koreanName;
        }

        public void setKoreanName(String koreanName) {
            this.koreanName = koreanName;
        }

        public String getEnglishName() {
            return englishName;
        }

        public void setEnglishName(String englishName) {
            this.englishName = englishName;
        }
    }

}
