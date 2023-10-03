package site.bitrun.cryptocurrency.dto;

import jakarta.validation.constraints.NotEmpty;

public class SellCryptoForm {

    private String sellMarketCode;

    private String sellType; // 지정가 OR 시장가

    private double sellCount; // 매도 수량

    public SellCryptoForm(String sellMarketCode, String sellType, double sellCount) {
        this.sellMarketCode = sellMarketCode;
        this.sellType = sellType;
        this.sellCount = sellCount;
    }

    public String getSellMarketCode() {
        return sellMarketCode;
    }

    public String getSellType() {
        return sellType;
    }

    public double getSellCount() {
        return sellCount;
    }
}
