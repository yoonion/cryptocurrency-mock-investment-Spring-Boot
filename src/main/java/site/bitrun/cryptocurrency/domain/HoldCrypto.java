package site.bitrun.cryptocurrency.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.bitrun.cryptocurrency.global.api.upbit.domain.UpbitMarket;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class HoldCrypto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hold_crypto_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "upbit_market_id")
    private UpbitMarket upbitMarket;

    private double buyCryptoCount; // 보유 코인 수량
    private double buyAverage; // 평단가
    private long buyTotalKrw; // 총 매수 금액(KRW)

    public HoldCrypto(Member member, UpbitMarket upbitMarket, double buyCryptoCount, double buyAverage, long buyTotalKrw) {
        this.member = member;
        this.upbitMarket = upbitMarket;
        this.buyCryptoCount = buyCryptoCount;
        this.buyAverage = buyAverage;
        this.buyTotalKrw = buyTotalKrw;
    }
}
