package site.bitrun.cryptocurrency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.bitrun.cryptocurrency.domain.CryptoRank;

public interface CryptoRepository extends JpaRepository<CryptoRank, Long> {
}
