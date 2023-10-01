package site.bitrun.cryptocurrency.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class BuyCryptoForm {

    private String tradeType; // buy(매수) OR sell(매도)

    private String buyMarketCode;

    private String buyType; // 지정가 OR 시장가

    private String buyKrw;

    public BuyCryptoForm(String tradeType, String buyMarketCode, String buyType, String buyKrw) {
        this.tradeType = tradeType;
        this.buyMarketCode = buyMarketCode;
        this.buyType = buyType;
        this.buyKrw = buyKrw;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getBuyMarketCode() {
        return buyMarketCode;
    }

    public void setBuyMarketCode(String buyMarketCode) {
        this.buyMarketCode = buyMarketCode;
    }

    public String getBuyType() {
        return buyType;
    }

    public void setBuyType(String buyType) {
        this.buyType = buyType;
    }

    public String getBuyKrw() {
        return buyKrw;
    }

    public void setBuyKrw(String buyKrw) {
        this.buyKrw = buyKrw;
    }
}
