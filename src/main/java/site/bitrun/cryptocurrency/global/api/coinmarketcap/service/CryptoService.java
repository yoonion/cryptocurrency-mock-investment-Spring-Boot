package site.bitrun.cryptocurrency.global.api.coinmarketcap.service;

import site.bitrun.cryptocurrency.global.api.coinmarketcap.domain.CryptoRank;

import java.util.List;

public interface CryptoService {

    void saveCryptoRankList();

    List<CryptoRank> getCryptoRankList();
}
