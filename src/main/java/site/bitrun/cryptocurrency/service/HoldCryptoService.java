package site.bitrun.cryptocurrency.service;

import site.bitrun.cryptocurrency.domain.Member;
import site.bitrun.cryptocurrency.global.api.upbit.domain.UpbitMarket;

public interface HoldCryptoService {

    void buyCrypto(long memberId, String marketCode, long buyKrw);
}
