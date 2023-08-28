package site.bitrun.cryptocurrency.service;

import site.bitrun.cryptocurrency.domain.CryptoRank;

import java.util.List;

public interface CryptoService {

    void saveCryptoRankList();

    List<CryptoRank> getCryptoRankList();
}
