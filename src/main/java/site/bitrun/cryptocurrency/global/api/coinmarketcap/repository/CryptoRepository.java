package site.bitrun.cryptocurrency.global.api.coinmarketcap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.bitrun.cryptocurrency.global.api.coinmarketcap.domain.CryptoRank;

public interface CryptoRepository extends JpaRepository<CryptoRank, Long> {
}
