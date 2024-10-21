package site.bitrun.cryptocurrency.dto;

import lombok.Getter;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Getter
public class HoldCryptoDto {

    private final String marketCode;
    private final String marketCodeOnlySymbol;
    private final String koreanName;
    private final double holdCount;
    private final double buyAverage;
    private final double buyTotalKrw;
    private final String symbolMd5Hash;

    public HoldCryptoDto(String marketCode, String marketCodeOnlySymbol, String koreanName, double holdCount, double buyAverage, double buyTotalKrw) {
        this.marketCode = marketCode;
        this.marketCodeOnlySymbol = marketCodeOnlySymbol;
        this.koreanName = koreanName;
        this.holdCount = holdCount;
        this.buyAverage = buyAverage;
        this.buyTotalKrw = buyTotalKrw;
        this.symbolMd5Hash = getMd5HashBySymbol(marketCodeOnlySymbol);
    }

    // 빗썸 이미지는 심볼을 md5 해시해둔 규칙이 있음. 해당 이미지 경로를 가져오기 위해 MD5 해시값을 함께 전달
    private String getMd5HashBySymbol(String symbol) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(symbol.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            return String.format("%032x", no);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
