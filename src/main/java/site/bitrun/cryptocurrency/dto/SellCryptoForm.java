package site.bitrun.cryptocurrency.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class SellCryptoForm {

    private final String sellMarketCode;

    private final String sellType; // 지정가 OR 시장가

    @NotEmpty
    private final String sellCount; // 매도 수량

    public SellCryptoForm(String sellMarketCode, String sellType, String sellCount) {
        this.sellMarketCode = sellMarketCode;
        this.sellType = sellType;
        this.sellCount = sellCount;
    }

}
