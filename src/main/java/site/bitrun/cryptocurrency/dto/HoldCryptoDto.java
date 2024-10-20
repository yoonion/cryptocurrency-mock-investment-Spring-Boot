package site.bitrun.cryptocurrency.dto;

import lombok.Getter;

@Getter
public class HoldCryptoDto {

    private String marketCode;
    private String marketCodeOnlySymbol;
    private String koreanName;
    private double holdCount;
    private double buyAverage;
    private double buyTotalKrw;

    public HoldCryptoDto(String marketCode, String marketCodeOnlySymbol, String koreanName, double holdCount, double buyAverage, double buyTotalKrw) {
        this.marketCode = marketCode;
        this.marketCodeOnlySymbol = marketCodeOnlySymbol;
        this.koreanName = koreanName;
        this.holdCount = holdCount;
        this.buyAverage = buyAverage;
        this.buyTotalKrw = buyTotalKrw;
    }
}
