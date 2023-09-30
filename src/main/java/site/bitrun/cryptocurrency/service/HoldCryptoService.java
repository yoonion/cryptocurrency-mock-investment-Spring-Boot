package site.bitrun.cryptocurrency.service;

import site.bitrun.cryptocurrency.domain.HoldCrypto;
import site.bitrun.cryptocurrency.dto.HoldCryptoDto;

import java.util.List;

public interface HoldCryptoService {

    void buyCrypto(long memberId, String marketCode, long buyKrw); // 매수

    List<HoldCryptoDto> getHoldCryptoList(long memberId); // 보유 암호화폐 list

//    HoldCryptoDto getHoldCryptoOne(long memberId, long upbitMarketId); // 보유 암호화폐 1개

}
