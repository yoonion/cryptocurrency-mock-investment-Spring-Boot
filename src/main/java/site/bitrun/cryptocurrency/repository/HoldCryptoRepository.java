package site.bitrun.cryptocurrency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import site.bitrun.cryptocurrency.domain.HoldCrypto;

public interface HoldCryptoRepository extends JpaRepository<HoldCrypto, Long> {

    @Query("select h from HoldCrypto h where h.member.id = :memberId and h.upbitMarket.id = :upbitMarketId")
    HoldCrypto findByMemberIdAndUpbitMarketId(@Param("memberId") Long memberId, @Param("upbitMarketId") Long UpbitMarketId);
}