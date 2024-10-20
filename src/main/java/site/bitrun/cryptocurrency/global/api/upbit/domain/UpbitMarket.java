package site.bitrun.cryptocurrency.global.api.upbit.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class UpbitMarket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "upbit_market_id")
    private Long id;

    @Column(name = "market_code")
    private String market;

    private String koreanName;

    private String englishName;

    public UpbitMarket(String market, String koreanName, String englishName) {
        this.market = market;
        this.koreanName = koreanName;
        this.englishName = englishName;
    }
}
